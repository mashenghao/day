package bLife;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * channel生命周期 与 各周期回调方法调用：
 * <p>
 * 下面定义了两个ChannelInboundHandler的实现类， 一个功能是为新来的channel进行初始化，将channel添加处理handler。
 * 另一个是具体的业务处理逻辑handler。 这两个handler都是实现了所有的ChannelInboundHandler的回调接口，用来测试netty的
 * channel的调用链路。
 * 预估的是，ChannelInitializer这个handler的所有回调都先与调用SimpleChannelInboundHandler的相同回调接口，因为可以理解
 * ChannelInitializer是责任链模式的调用头。
 * <p>
 * 答案:
 * 测试运行结果是
 * 1. ChannelInitializer除了注册抽象接口被调用了，其他的都没被调用。
 * 具体业务实现的handler的各个回调接口都被调用了， 顺序是：
 * --- ChannelInitializer --- initChannel 方法被调用了
 * channel - channelRegistered 调用   -------------------------这个channel的注册实现回调接口也会被调用，即使是ChannelInitializer 已经调用过了
 * channel - channelActive 调用
 * channel - channelRead0 调用
 * channel - channelRead0 调用
 * channel - channelReadComplete 调用
 * channel - channelReadComplete 调用
 * channel - channelInactive 调用
 * channel - channelUnregistered 调用
 *
 * 2. 如果自定义实现ChannelInitializer，里面注册http的处理器，则整个回调接口都会被调用的。
 * channel - handlerAdded 调用   --  add回调先于register
 * channel - channelRegistered 调用
 * channel - channelActive 调用
 * channel - channelRead0 调用
 * channel - channelRead0 调用
 * channel - channelReadComplete 调用
 * channel - channelReadComplete 调用
 * channel - channelInactive 调用
 * channel - channelUnregistered 调用
 * channel - handlerRemoved 调用   --- 最后是移除handler
 *
 *
 * @author mahao
 * @date 2022/10/14
 */
public class LiftServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup)
                .channel(NioServerSocketChannel.class)

                /**
                 * 自己实现的 注册channel的实现类，各个回调都会被调用。
                 */
                .childHandler(new SimpleChannelInboundHandler<Object>() {
                    /**
                     //                             * 用来定义业务含义的。
                     //                             * @param ctx
                     //                             * @param msg
                     //                             */
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
                        System.out.println("channel - channelRead0 调用");
//                        ctx.fireChannelRead(msg);
                        ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
                        //构造处 http协议的返回体返回。
                        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                        ctx.writeAndFlush(response);
                    }

                    //最基础父类ChannelHandler定义的channel的接口回调。
                    /*
                    发生在一个新的通道需要建立了，也就是ServerSocket已经accepted一个客户端的socket了(也就是一个tcp连接建立了)，
                    会去触发这个accepted
                     */
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - handlerAdded 调用");
                        super.handlerAdded(ctx);
                    }

                    /**
                     * 当连接通道关闭后就会回调，也就是tcp断开了。
                     * @param ctx
                     * @throws Exception
                     */
                    @Override
                    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - handlerRemoved 调用");
                        super.handlerRemoved(ctx);
                    }

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - channelRegistered 调用");
                        ctx.pipeline().addFirst(new HttpServerCodec());
                        super.channelRegistered(ctx);
                    }

                    @Override
                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - channelUnregistered 调用");
                        super.channelUnregistered(ctx);
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - channelActive 调用");
                        super.channelActive(ctx);
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - channelInactive 调用");
                        super.channelInactive(ctx);
                    }

                    @Override
                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - channelReadComplete 调用");
                        super.channelReadComplete(ctx);
                    }

                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                        System.out.println("channel - userEventTriggered 调用");
                        super.userEventTriggered(ctx, evt);
                    }

                    @Override
                    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channel - channelWritabilityChanged 调用");
                        super.channelWritabilityChanged(ctx);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        System.out.println("channel - exceptionCaught 调用");
                        super.exceptionCaught(ctx, cause);
                    }
                });

        // 1. 这种方法，业务处理的handler都会调用， 但是ChannelInitializer 只有register会被调用，其他的回调不会去被发现调用的。
//                .childHandler(new ChannelInitializer<SocketChannel>() {
//                    /**
//                     * 该抽象方法是Channel注册的时候，也就是channelRegistered回调方法。功能就会将channel注册给
//                     * 用户， 让用户给这个channel添加处理的handler。
//                     * @param ch
//                     * @throws Exception
//                     */
//                    @Override
//                    protected void initChannel(SocketChannel ch) {
//                        System.out.println("--- ChannelInitializer --- initChannel 方法被调用了");
//                        ch.pipeline().addLast(new HttpServerCodec());
//                        ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpObject>() {
//                            /**
//                             * 用来定义业务含义的。
//                             * @param ctx
//                             * @param msg
//                             */
//                            @Override
//                            protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
//                                System.out.println("channel - channelRead0 调用");
//                                ctx.fireChannelRead(msg);
//                                ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
//                                //构造处 http协议的返回体返回。
//                                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
//                                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
//                                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
//                                ctx.writeAndFlush(response);
//                            }
//
//                            @Override
//                            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//                                System.out.println("channel - channelRegistered 调用");
//                                super.channelRegistered(ctx);
//                            }
//
//                            @Override
//                            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//                                System.out.println("channel - channelUnregistered 调用");
//                                super.channelUnregistered(ctx);
//                            }
//
//                            @Override
//                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                System.out.println("channel - channelActive 调用");
//                                super.channelActive(ctx);
//                            }
//
//                            @Override
//                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//                                System.out.println("channel - channelInactive 调用");
//                                super.channelInactive(ctx);
//                            }
//
//                            @Override
//                            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//                                System.out.println("channel - channelReadComplete 调用");
//                                super.channelReadComplete(ctx);
//                            }
//
//                            @Override
//                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//                                System.out.println("channel - userEventTriggered 调用");
//                                super.userEventTriggered(ctx, evt);
//                            }
//
//                            @Override
//                            public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//                                System.out.println("channel - channelWritabilityChanged 调用");
//                                super.channelWritabilityChanged(ctx);
//                            }
//
//                            @Override
//                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//                                System.out.println("channel - exceptionCaught 调用");
//                                super.exceptionCaught(ctx, cause);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//                        System.out.println("--- ChannelInitializer ---channelUnregistered 方法被调用了");
//                        super.channelUnregistered(ctx);
//                    }
//
//                    @Override
//                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                        System.out.println("--- ChannelInitializer ---channelActive 方法被调用了");
//                        super.channelActive(ctx);
//                    }
//
//                    @Override
//                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//                        System.out.println("--- ChannelInitializer ---channelInactive 方法被调用了");
//                        super.channelInactive(ctx);
//                    }
//
//                    @Override
//                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                        System.out.println("--- ChannelInitializer ---channelRead 方法被调用了");
//                        super.channelRead(ctx, msg);
//                    }
//
//                    @Override
//                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//                        System.out.println("--- ChannelInitializer ---channelReadComplete 方法被调用了");
//                        super.channelReadComplete(ctx);
//                    }
//
//                    @Override
//                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//                        System.out.println("--- ChannelInitializer ---userEventTriggered 方法被调用了");
//                        super.userEventTriggered(ctx, evt);
//                    }
//
//                    @Override
//                    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//                        System.out.println("--- ChannelInitializer ---channelWritabilityChanged 方法被调用了");
//                        super.channelWritabilityChanged(ctx);
//                    }
//                });

        ChannelFuture channelFuture = bootstrap.bind(9999).sync();
        channelFuture.channel().closeFuture().sync();


    }
}
