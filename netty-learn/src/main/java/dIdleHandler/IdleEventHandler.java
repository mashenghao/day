package dIdleHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 定义用户自定义 处理连接闲置超时的事件处理函数。
 *
 * @author mahao
 * @date 2022/10/17
 */
public class IdleEventHandler extends ChannelInboundHandlerAdapter {

    //简单打印日志
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            System.out.println(event.isFirst() + "   " + event.state());
        }
    }
}
