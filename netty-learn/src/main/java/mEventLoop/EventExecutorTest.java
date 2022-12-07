package mEventLoop;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * 单独的任务执行Executor。 executor的作用是从属性队列中去取任务，然后run方法一直循环运行。
 * executor负责管理队列， 真正创建的线程是来自JDK的Executor，这个Executor的创建来自与Group的构造函数。
 * <p>
 * Group会创建多个EventExecutor， 创建executor的时候，告诉EventExecutor线程获取从Executor中去取{@link ThreadPerTaskExecutor (newDefaultThreadFactory());}。
 * 在 group的构造函数中 {@link MultithreadEventExecutorGroup#MultithreadEventExecutorGroup(int, java.util.concurrent.Executor, io.netty.util.concurrent.EventExecutorChooserFactory, java.lang.Object...)}
 *
 * @author mahao
 * @date 2022/12/04
 */
@Slf4j
public class EventExecutorTest {
    public static void main(String[] args) {
        DefaultEventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(2);
        DefaultEventExecutor executor = (DefaultEventExecutor) eventExecutorGroup.next();
    }
}
