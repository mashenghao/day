package cSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDate;

/**
 * @author mahao
 * @date 2022/10/16
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("客户端--> " + ctx.channel().remoteAddress().toString() + " : " + msg);
        ctx.channel().writeAndFlush("from client : " + LocalDate.now());
        //关闭客户端到服务端的通道
//        ctx.close();
    }
}
