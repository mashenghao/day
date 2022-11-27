package jReactor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 主流程测试
 *
 * @author mahao
 * @date 2022/11/17
 */
public class MainProcessorTest {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //为serversocketChannel 添加个日志处理器
                .handler(new LoggingHandler("--server--"))
                // 给 NioSocketChannel添加处理器
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new LoggingHandler("--client--"));
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                int i = msg.readableBytes();
                                byte[] buf = new byte[i];
                                msg.readBytes(buf, msg.readerIndex(), i);
                                System.out.println("服务端收到信息  ->  " + new String(buf));

                                ctx.writeAndFlush(Unpooled.copyLong(System.currentTimeMillis()));
                            }
                        });

                    }
                });
        ChannelFuture sync = serverBootstrap.bind(9999).sync();
        System.out.println("==开启检测==");
        sync.channel().closeFuture().sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
