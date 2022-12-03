package kMainProcess;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author mahao
 * @date 2022/11/29
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(eventLoopGroup)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                        pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
                        pipeline.addLast(new SimpleChannelInboundHandler<String>() {

                            @Override
                            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                log.info("register");
                                ctx.writeAndFlush("13333333333333");
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                System.out.println(msg);
                                ctx.writeAndFlush("1334");
                                log.info("xietu");
                            }
                        });
                    }
                });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 9999));
        future.channel().closeFuture().sync();
    }
}
