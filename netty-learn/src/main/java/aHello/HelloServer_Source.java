package aHello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;

import java.util.List;

/**
 *
 * 二次理解： bossGroup是可以被主reactor和从reactor一起使用的
 *
 *1. 只用一个group，单线程reactor模式
 *2. 指定serverchannel的handler和每个客户端channel的handler。
 *3. 需要指定Server的channel类型的意义，为啥不用指定客户端channel
 *
 * @author mash
 * @date 2024/7/9 下午10:02
 */
public class HelloServer_Source {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(1); //用户接收连接

        ServerBootstrap bootstrap = new ServerBootstrap();
        //1. 只用一个group，单线程reactor模式
        bootstrap.group(group, group)
                /**
                 * 2. 为初始化好的ServerSocketChannel的Pipline添加上这个handler，这是倒数第二个Handler，
                 * 倒数第一个是acceptor {@link io.netty.bootstrap.ServerBootstrap.ServerBootstrapAcceptor}（用来对新的连接注册到workerGroup中）。
                 *
                 */
                .handler(new LoggingHandler())
                /**
                 * 3. 指定服务channel是NioServerSocketChannel, 需要指定服务channel,是因为{@link NioServerSocketChannel#doReadMessages(List)}需要自定义化
                 * 读取到的连接类型。 只需呀定义服务端channel，客户端channel跟着服务端走。
                 */
                .channel(NioServerSocketChannel.class)
                /**
                 *4. childHandler是给当前ServerChannel接到的链接绑定的初始化handler。 实现的方案是channelRegistered函数回调，
                 * 将ch传入到到回调函数中，让用户调用，之后就讲自己移除pipLine。
                 */
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //3.1 为流水线添加自己的解析，已有的流水线，应该是处理socket中的流数据，这里添加
                        //个http的解析器，将数据解析为http信息。
                        ch.pipeline().addLast("httpServerCodec", new HttpServerCodec());
                        //3.2 解析成http信息后，定义自己业务处理逻辑，这里定义了一个handler，简单的写会数据。
                        ch.pipeline().addLast("helloHander", new HelloClientHandler(" --- helloHander ---"));
                    }
                });


        ChannelFuture bind = bootstrap.bind(9999);
        bind.sync();

        System.out.println("bind 完成了");

        //监听closeFuture完成
        bind.channel().closeFuture().sync();
    }
}
