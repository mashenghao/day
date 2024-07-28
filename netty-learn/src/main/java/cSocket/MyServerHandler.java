package cSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author mahao
 * @date 2022/10/16
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("服务端--> " + ctx.channel().remoteAddress().toString() + " : " + msg);
        ctx.channel().writeAndFlush("from server : " + UUID.randomUUID()); //从尾到头
//        ctx.writeAndFlush() //从当前节点开始
        //关闭服务端到客户端的通道 ，接收到客户端的一个消息后，就关闭了服务端到客户端方向的通道。
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush("客户端连接成功后，发送的第一个问候信息，触发客户端的channelRead0方法");
    }
}
