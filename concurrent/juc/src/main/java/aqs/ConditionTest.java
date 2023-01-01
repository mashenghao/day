package aqs;

import atomic.ThreadUtil;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 多条件wait队列：
 * 实现 就是每个Condition中都有一个wait队列，在这个Condition上调用await的线程Node，
 * 就会进入队列中，**同时会去调用lock的release方法，将当前线程持有的锁，全部释放。**
 * 其他线程调用Condition的signal后，就会去唤醒wait队列中的Node线程。
 * 被唤醒的Node线程就会重新进入Lock的AQS的同步队列中，重新去获取锁。
 *
 * @author mahao
 * @date 2022/06/13
 */
public class ConditionTest {

    /**
     * Condition原理:
     * 1. Condition.await:
     * 1.1  使当前阻塞到条件1当中， 实现是当前线程Node，添加到Condition的wait队列中，
     * 1.2 获取到当前线程获取到的锁个数 saveMode， 然后调用 release，释放锁(必须持有锁), 之后当前线程就会陷入park中，等待唤醒。
     * 1.3 当前线程被唤醒后，会重新进入Lock的AQS同步队列中，等待获取锁，获取锁的个数为saveMode保存的个数。
     * 2. Condition.signal: 唤醒wait到当前条件的线程，从内部的wait队列中，去取Node,调用unpark方法，之后就完成了。
     */
    @Test
    public void test() {
        ReentrantLock lock = new ReentrantLock(true);
        Condition con1 = lock.newCondition();
        Condition con2 = lock.newCondition();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 开始运行");
                //1. 调用await会去释放Condition持有的锁，也就是调用release方法。 如果没有持有锁，就会IllegalMonitorStateException
                //2. 中断了，会抛出中断异常
                //3. 唤醒后，进入Lock的同步队列，重新等待获取锁。
                con1.await();
                System.out.println("t1 运行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t2 开始运行");
                con2.await();
                System.out.println("t2 运行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();

        ThreadUtil.sleep(3000);
        System.out.println("main线程3秒后,唤醒t2 线程");
        lock.lock();
        con2.signal();
        lock.unlock();

        System.out.println("main 结束 。。。");
    }

}
