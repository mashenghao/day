package aqs;

import atomic.ThreadUtil;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. ReentrantLock的lock 和 lockInterruptibly,普通锁和中断锁。
 *
 * @author mahao
 * @date 2022/06/10
 */
public class ReentrantLockTest {
    /**
     * 阻塞等待锁，直到持有锁为止，不可被中断。
     *
     * @throws InterruptedException
     */
    @Test
    public void testLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        System.out.println("main 线程拿到锁");

        Thread t1 = new Thread(() -> {
            System.out.println("t1 线程开始获取锁运行,");
            lock.lock();
            System.out.println("t1 线程拿到锁后,运行结束: " + Thread.currentThread().isInterrupted());
            lock.unlock();
        });
        t1.start();

        Thread.sleep(3000);
        /*
        调用中断方法，会将线程唤醒（LockSupport.park会被中断唤醒）,但线程唤醒后，又会进行阻断，直到获取到锁后，
        会将线程的状态置为中断。 也就是lock.lock();方法，会一直执行，直到获取到锁。
         */
        System.out.println("main线程持有锁调用t1的中断方法。");
        t1.interrupt();


        Thread.sleep(3000);
        lock.unlock();

        Thread.sleep(10000);
    }

    /**
     * 在获取锁的时候，如果线程被中断了，这个锁就会抛出异常。
     *
     * @throws InterruptedException
     */
    @Test
    public void testLockInterruptibly() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        System.out.println("main 线程拿到锁");

        Thread t1 = new Thread(() -> {
            System.out.println("t1 线程开始获取锁运行,");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("t1 线程锁被中断,运行结束: " + Thread.currentThread().isInterrupted());
                return;
            }
            System.out.println("t1 线程拿到锁后,运行结束: " + Thread.currentThread().isInterrupted());
            lock.unlock();
        });
        t1.start();
        Thread.sleep(3000);
        System.out.println("main线程调用t1的中断，则t1 线程将会放弃争取锁，抛出中断异常");
        t1.interrupt();

        lock.unlock();

    }

    /**
     * 尝试加锁，用tryAcquire()方法实现的。
     */
    @Test
    public void testTryLock() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.tryLock();
        try {
            lock.tryLock(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
