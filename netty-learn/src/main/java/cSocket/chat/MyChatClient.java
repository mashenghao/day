package cSocket.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * client 需要先去server 注册自己的channel，注册完成后，需要个循环
 * 监听键盘的输入，将输入的信息写会给服务端。
 * <p>
 * 客户端启动类
 *
 * @author mahao
 * @date 2022/10/16
 */
public class MyChatClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //解码handler
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                        pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));

                        pipeline.addLast(new MyChatClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();

        //这里不需要在阻塞等待监控channel关闭，需要自己有个循环，将键盘输入发给服务端。
        //channelFuture.channel().closeFuture().sync();
        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            channelFuture.channel().writeAndFlush(scanner.next() + "\n");
        }
    }
}
