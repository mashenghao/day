package mEventLoop;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 事件循环与事件循环组关系
 *
 * @author mahao
 * @date 2022/12/04
 */
public class EventLoopGroupTest {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        EventLoop eventLoop = eventLoopGroup.next();
        NioEventLoop eventLoop1 = (NioEventLoop) eventLoop;
    }
}
