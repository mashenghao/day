package dIdleHandler;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 1. {@link IdleStateHandler} 超时校验handler的使用， 这个handler超过执行时间检测到channel没有进行
 * 读写操作，就会调用fireUserEventTriggered()方法，这个方法就会触发各个Handler的userEventTriggered()
 * 回调的方法。
 * <p>
 * 2.IdleStateHandler的实现是通过为每个Channel设置个定时任务Task。 IdleStateHandler实现了回调函数channelRegistered(),
 * 当有Channel注册的时候，就会为这个channel添加定时task，每隔设定的时间运行一次task，task的功能是检查下上次读取、写入、读与写的时间
 * 与现在时间间隔是否大于设定的超时时间， 如果超过了，则创建一个时间，调用fireUserEventTriggered()触发之后的handler的userEventTriggered()方法。
 * {@link IdleStateHandler.ReaderIdleTimeoutTask#run(ChannelHandlerContext)}。
 * <p>
 * 3.IdleStateHandler 有3个整数类型参数， 分别是read write all，三个channel操作超时时间， 如果小于0, 则不做定时监控， 配置了几个，会去启动几个task，
 * 执行线程是从ChannelHandlerContext 中获取到的，
 *
 * @author mahao
 * @date 2022/10/17
 */
public class IdleServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(5, 10, 12));
                        pipeline.addLast(new IdleEventHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
        channelFuture.channel().closeFuture().sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
