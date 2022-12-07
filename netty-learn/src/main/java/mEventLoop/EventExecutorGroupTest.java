package mEventLoop;

import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * 事件执行组测试：
 * <p>
 * 该Group的功能就是负责维护executor，然后调用内部的执行器执行任务。任务类型都是runnable，没有业务意义。
 * <p>
 * 1. DefaultEventExecutorGroup 是 EventExecutorGroup的默认实现。 他的的父类{@link AbstractEventExecutorGroup}实现了
 * 相关的执行方法，内部是调用next()然后执行。 next()的实现在{@link MultithreadEventExecutorGroup}，他维护了一组{@link io.netty.util.concurrent.EventExecutor}
 * ，通过chooser轮旋选择一个EventExecutor。
 * <p>
 * 2.DefaultEventExecutorGroup的功能是创建EventExecutor，提供给父类去管理。
 *
 * @author mahao
 * @date 2022/12/04
 */
@Slf4j
public class EventExecutorGroupTest {

    public static void main(String[] args) throws InterruptedException {

        DefaultEventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(2);
        //两次是不同的线程
        Future<?> future = eventExecutorGroup.submit(() -> {
            log.info("执行了");
        });
        Future<?> future2 = eventExecutorGroup.submit(() -> {
            log.info("执行了2");
        });

        future.sync();
        future2.sync();

        eventExecutorGroup.shutdownGracefully();
    }
}
