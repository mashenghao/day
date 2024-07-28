package aHello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * hello world
 *
 * @author mahao
 * @date 2022/10/12
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {
        //1. 定义两个事件循环组， 两个有不同的用处，事件循环组，就是死循环。
        //  bossGroup用于一直等待接收客户端的accept，之后将接受的到的连接发送给
        //workerGroup， work会对接收到的连接，在workGroup组的线程内进行读取数据，
        // 根据配置的pip line进行数据的处理，包括用户程序的处理逻辑也都是worker group内。
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //用户接收连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();//用于处理boss接收到连接

        //2. netty提供的启动服务的工具类。
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //2.1 设置给启动器 接收连接的事件组 与 处理事件的线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    //2.2 类似于ServerSocket,用来accept客户端的连接。 这里类比一下，用来接收netty
                    //客户端的channel。
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    //2.3 这里是给每个连接到NioServerSocketChannel的连接channel,
                    // 当连接好后，调用这个handler的初始化方法，给这个channel设置用户程序的处理逻辑。
                    // 以后该channel的中的数据读写操作逻辑， 都是在这个方法中设立的。
                    .childHandler(new HelloInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();

            workerGroup.shutdownGracefully();
        }


    }
}
