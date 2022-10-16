package cSocket.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;

/**
 * 服务端的handler 自定义实现channel各个状态的回调函数，当出现指定状态时，通知所有注册
 * 到serverChannel的channel。
 *
 * @author mahao
 * @date 2022/10/16
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //一个会注册到netty server上的set，会维护注册到这个组上的channel的状存活态。
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //负责将ctx收到的消息，转发给其他的channel。
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println(ctx.channel().remoteAddress() + " : " + msg);
        for (Channel channel : channels) {
            if (channel.equals(ctx.channel())) {
                ctx.writeAndFlush("[自己] 发送的消息: " + msg);
            } else {
                channel.writeAndFlush(ctx.channel().remoteAddress() + " 发送的消息: " + msg);
            }
        }
    }


    //处理新的channel加入，将其添加到channel 组中，并且发送消息给其他的channel。
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[服务器] " + channel.remoteAddress() + " 加入\n");

        channels.add(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线\n");
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[服务器] " + channel.remoteAddress() + " 离开\n");

        //无需调用，ChannelGroup会进行维护的。
//        channels.remove(channel);
    }
}
