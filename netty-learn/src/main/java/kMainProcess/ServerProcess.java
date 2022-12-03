package kMainProcess;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * netty服务端 运行主流程与组件之间运行关系：
 * (NioServerSocketChannel.class)主启动流程:
 * 1.ServerBootStarp 协调 NioServerSocketChannel的初始化与注册，里面含有handler，childHandler，bossGroup和workerGroup。
 * 2. 反射方式初始化NioServerSocketChannel，NioServerSocketChannel继承自Channel和ServerChannel。 每个channel都继承AbstractChannel，
 * 里面实现了构造channel的pipline对象与Unsafe对象。NioMessageUnsafe实现。
 * 3. BootStarp会为channel进行初始化， 为channel设置属性， channle的pipline添加handler， 也会添加一个内置的ServerBootstrapAcceptor的handler。
 * ServerBootstrapAcceptor会被延迟添加到pipline中。
 * 4.初始化完成后，需要把这个serverChannel注册到bossGroup中。BootStarp 将这个初始化好的channel，调用bossgroup去注册。 bossgroup选择一个EventLoop去注册
 * 其中的一个线程中，最终调用方法落到AbstractChannel.Unsfae的register方法上了，参数是EventLoop与promise(外部回调)。
 * 5.Unsafe实现是NioMessageUnsafe注册方法在AbstractUnsafe内，注册要求必须在EventLoop线程内。 ServerBootStrap发起注册，只是将注册任务提交到bossGroup的
 * 任务队列中。 下次循环就注册了，注册逻辑会向bossgroup的selector注册这个ServerChannel。 之后内部的回调函数就被调用了：
 *    5.1 注册后，pipline上的handlerAdd()方法被调用，包含ChannelInitializer的注册被调用。
 *    5.2 channelRegister()回调链路被触发
 *    5.3 promise 被设置状态成功，外部添加的监听任务doBind0()会在当前线程内执行， 这个doBind0()操作会下次事件循环触发chanenl的bind()回调方法链，这个链的head,做的
 *    操作是将javaChannel.bind() 做的整整bind操作。
 *6.上面操作完，服务端已经可以接收了，当有客户channel接入，bossGroup的EventLoop的循环方法将会获取到channel注册的Accept事件，交由放到SelectKey上的Channel去处理，
 * channel会调用UnSafe下的read方法，不同channel定义的读的类型不同，ServerSocketChannl的读就是Accpet时间，结果值是SocketChannel.客户端读的是数据包了。 之后会将这个用accept到的chanel
 * 触发channelRead()调用链路。
 *7. chaannelRead最后一个handler是ServerBootstrapAcceptor， 他会将连接的channel的pipline添加childHandler，让后注册给workerGroup。
 *
 * @author mahao
 * @date 2022/11/27
 */
@Slf4j
public class ServerProcess {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(3);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                //1. 指定事件循环组，也就是两个线程池，一个mainReactor运行线程组，一个是子reactor线程组。
                .group(bossGroup, workerGroup)
                //2.指定ServerSocketChannel的类型，这里的是非阻塞io
                .channel(NioServerSocketChannel.class)
                //3. 指定ServerSocketChannel的handler，这个是加载Acceptor Handler之后的。
                .handler(new LoggingHandler())
                //4. 当主reactor把连接分到子reactor后，连接到子reactor的channel，添加handler，
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                        pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));


                        //1. 给channel添加channelInboundHandler,当有连接运行到固定的状态时，回调函数会被调用，
                        //调用链也将会被ChannelHandlerContext链式调用到。
                        //2. DefaultChannelPipeline是ChannelPipeline的默认实现，每个channel都有一个pipeline,这个pipeline
                        //是暴露给主流程的，pipeline中有触发责任链头的方法fireChannelRegistered(), 会被channel处理的过程中处理。
                        ch.pipeline().addLast(new ChannelInboundHandler() {
                            @Override
                            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

                                log.info("in-- register --- 当channel 被注册时候回去调用");
                                //触发下个handler的调用。
                                ctx.fireChannelRegistered();
                            }

                            @Override
                            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                log.info("in -- unRegister --- channel 被不注册");
                                ctx.fireChannelUnregistered();
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                log.info("in --- active --");
                                ctx.fireChannelActive();
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                log.info("in --  inactive   ");
                                ctx.fireChannelInactive();
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("in ---- channel可读了， 数据 【{}】", msg);
                                ctx.fireChannelRead(msg);
                            }

                            @Override
                            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                log.info("in --  readComplete");
                                ctx.fireChannelReadComplete();
                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                System.out.println("in ---- userEventTriggered");
                                ctx.fireUserEventTriggered(evt);
                            }

                            @Override
                            public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                                log.info("in ----- channelWritabilityChanged");
                                ctx.fireChannelWritabilityChanged();
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                log.info("in -- 异常了");
                                ctx.fireExceptionCaught(cause);
                            }

                            @Override
                            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                log.info("in ---- handlerAdded ");
                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                log.info("in  --- handlerRemoved");
                            }
                        });
                    }
                });

        ChannelFuture sync = serverBootstrap.bind(9999).sync();
        sync.channel().closeFuture().sync();
    }
}
