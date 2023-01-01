package aqs;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 测试LockSupport的暂停和唤醒线程:
 * 1. LockSupport.park(this); 暂停线程，并将线程的锁对象设置为this对象.
 * 可被LocakSupport.unPark唤醒和线程中断方法唤醒
 * <p>
 * 2.  LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3)); 指定多久后被自动唤醒。
 * <p>
 * 3.unPark 作用： 可以唤醒一个被park的线程
 *
 * @author mahao
 * @date 2022/06/10
 */
public class LockSupportTest {


    /**
     * LockSupport.park(this); 暂停线程，并将线程的锁对象设置为this对象.
     * 可被LocakSupport.unPark唤醒和线程中断方法唤醒
     *
     * @throws Exception
     */
    @Test
    public void testLockSupport() throws Exception {
        Thread t1 = new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("我是线程t1，我被调度了");
                LockSupport.park(this);
                System.out.println("我是线程t1，我被唤醒了" + Thread.currentThread().isInterrupted());
            }
        });
        t1.start();

        Thread.sleep(3000);
        t1.interrupt();
        //this.notifyAll(); 不会唤醒被park阻塞的线程的
        Thread.sleep(300000);
        LockSupport.unpark(t1);
        Thread.sleep(1000);
        System.out.println("main唤醒t1后结束");

    }

    @Test
    public void testParkNanos() {
        System.out.println("main 线程开始运行了");

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3));

        System.out.println("main线程3秒后，暂停结束后开始运行了。");
    }

    @Test
    public void testParkUntil() {
        System.out.println("main 线程开始运行了");

        //阻塞，直到运行到一个绝对的时间。
        LockSupport.parkUntil(System.currentTimeMillis() + 3000);

        System.out.println("main线程3秒后，暂停结束后开始运行了。");
    }

    /**
     * unPark 作用：
     * 1. unPark一个park的线程，会唤醒这个线程
     * 2. unPark一个没有被park的线程，这个线程在进行一次park时，不会阻塞。
     */
    @Test
    public void testUnPark() {
        System.out.println("main 线程开始运行了");

        //当前线程没有被park，这里首先unpark一下
        LockSupport.unpark(Thread.currentThread());
        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();
        System.out.println("第一次调用park,输出结果说明没被阻塞");

        LockSupport.park();
        System.out.println("第二次调用park,没输出结果说明被阻塞了");
    }
}
