package mEventLoop;

import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 *
 * EventLoop 类继承结构:
 *
 * 第一层： Executor  和 ScheduleExecutorService 实现了EventLoop具备执行任务的能力，主要实现 JDK的AbstractExecutorService
 *
 * 第二层： EventExecutor， 提供了netty的Executor的能力，主要是newPromise(),parent(),next()方法，具体实现是AbstractEventExecutor和
 * SingleThreadEventExecutor。  SingleThreadEventExecutor独自占用一个线程资源，去处理添加到EventExecutor中的任务。
 *
 * 第三层：EventLoop 提供是事件注册的能力，  SingleThreadEventLoop 和 NioEventLoop ，NioEventLoop 提供具体的run方法执行逻辑。
 *
 *
 *
 * @author mash
 * @date 2024/7/14 14:58
 */
public class EventLoopStruct {

    public static void main(String[] args) {
        NioEventLoop nioEventLoop = null;



    }
}
