package oChannelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.logging.LoggingHandler;

/**
 * HeadContext  和 TailContext:
 *
 * handler被添加是channel被接入，给他设置handler。
 *
 *
 * @author mash
 * @date 2024/7/17 下午11:44
 */
public class HeadAndTailContext {

    public static void main(String[] args) {

        SimpleChannelInboundHandler<String> handler = new SimpleChannelInboundHandler<String>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

                //添加一个handler
                ctx.pipeline().addLast(new LoggingHandler());
                ChannelPromise channelPromise = ctx.channel().newPromise();


                /**
                 * 核心方法:
                 *
                 *
                 *
                 */
                ctx.channel().writeAndFlush("", channelPromise);
            }
        };


    }
}
