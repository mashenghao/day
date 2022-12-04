package lFuture;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * netty对Future/Promise 值与计算逻辑分离异步编程实现。
 * 1.promise 可以添加监听，监听程序执行线程是创建promise的时候指定的线程
 * ** 不可以在promise的executor线程内调用wait方法，因为一般情况下，计算线程就是executor， 如果还在计算线程内调用wait，就一直阻塞了。***
 * <p>
 * 2.DefaultPromise实现了listener的添加与唤醒方法，唤醒方法执行的线程是在executor内。listener有io.netty.defaultPromise.maxListenerStackDepth深度限制，
 * 可以在listener中继续创建future，添加listener，但是最深允许8个。多了就不让执行了。
 * <p>
 * 3.sync() get() await()这些方法可以同步阻塞获取到Promise，直到isDone()=ture,这些方法调用不能和executor不能是同一个，会抛出异常，这种情况下大概率会死锁。
 *
 * @author mahao
 * @date 2022/12/03
 */
public class FutureTest1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);

        /**
         * 知识点一: DefaultPromise 是默认实现，实现了Promise接口，也负责listener的添加与唤醒。
         * 构造函数是需要传入一个executor，这个负责回调函数的调用。
         *
         * Get the executor used to notify listeners when this promise is complete.
         *等价于： eventLoopGroup.next().newPromise();
         */
        EventLoop executors = eventLoopGroup.next(); //唤醒监听器的线程
        DefaultPromise<String> promise = new DefaultPromise<>(executors);

        //知识点二: 可以添加一个或者多个监听, 添加监听的逻辑就是在promise中属性listeners 存储这个listen，类型是Object，可能是单个
        //listener 也可能是List<Listenet>，执行线程都是我们传进去的eventLoopGroup。
        promise.addListener(future -> {
            System.out.println("listener1被调用 " + Thread.currentThread().getName());
        });
        promise.addListener(future -> {
            System.out.println("listener2被调用 " + Thread.currentThread().getName());
        });


        //知识点三： 通过设置promise的setSuccess或者失败方法可以对promise计算标记为完成，外部就可以获取到返回值了。
        eventLoopGroup.schedule(() -> {
            System.out.println("设置返回执行成功。" + Thread.currentThread().getName());
            promise.setSuccess("返回结果成功");
        }, 10, TimeUnit.SECONDS);

        ScheduledFuture<?> future = eventLoopGroup.schedule(() -> {
            System.out.println("第二次设置就会报错" + Thread.currentThread().getName());
            promise.setSuccess("第二次再去设置，就会报错");
        }, 15, TimeUnit.SECONDS);

        /**
         * 知识点四，不可以在promise同一个executor内调用阻塞方法wait，这里不明显。 一般情况下，promise的计算线程就是executor，如果还是在executor内
         * 调用阻塞方法。想像一下，如果阻塞任务task被先执行，这个线程就一直陷入阻塞中了，引入需要等待计算线程完成promise，但是计算线程轮到调度就陷入阻塞了。
         */
        executors.execute(() -> {
            try {
                System.out.println("线程内执行同步等待了，这种情况下该抛出异常");
                promise.sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //知识点四: 调用sync方法，就会在当前线程内阻塞，直到future 完成isDone()=ture。 内部调用的是await()方法， 包括get()也是调用的await(),
        //await()实现就是在promise当前实例上调用wait()方法， 被唤醒是在promise的set相关方法被调用的时候， 在promise改标记promise的状态的时候。
        //sync 会抛异常，如果promise设置的是exception。
        promise.sync();
        System.out.println("promise的结果返回了");


        //如果已经执行完毕后的，再次添加监听，则会立即执行
        promise.addListener(f -> {
            System.out.println("listener3被调用 " + Thread.currentThread().getName());
        });

        future.get();


    }
}
