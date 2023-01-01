package aqs;

import atomic.ThreadUtil;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * jstack 命令查看线程的状态
 * <p>
 * <p>
 * 1. t3 和 t4 线程都在等待获取锁，两个线程的状态为Blocked， 都是等待同一个锁waiting to lock <0x0000000740fc6238>。
 * 2. t2 获取到锁locked <0x0000000740fc6238>，所以一直运行态。
 * 3. t1 是自己陷入wait，持有到锁后，又陷入wait状态，- waiting on <0x0000000740fc6238>
 * 4. BLOCKED 和 WAITING 线程状态不一致，阻塞要做的操作也是不一样的。
 * BLOCKED： 等待锁状态BLOCKED，操作是 waiting to lock， 线程上是waiting for monitor entry
 * WAIT: wait状态是为 wait on 锁， 线程上是 Object.wait()
 * <p>
 * "thread -- t4" #15 prio=5 os_prio=0 tid=0x0000000017835000 nid=0x21a4 waiting for monitor entry [0x000000001875f000]
 * java.lang.Thread.State: BLOCKED (on object monitor)
 * at aqs.JstackTest$4.run(JstackTest.java:88)
 * - waiting to lock <0x0000000740fc6238> (a java.lang.Class for aqs.ReentrantLockTest)
 * <p>
 * "thread -- t3" #14 prio=5 os_prio=0 tid=0x0000000017832000 nid=0x3b34 waiting for monitor entry [0x000000001865f000]
 * java.lang.Thread.State: BLOCKED (on object monitor)
 * at aqs.JstackTest$3.run(JstackTest.java:76)
 * - waiting to lock <0x0000000740fc6238> (a java.lang.Class for aqs.ReentrantLockTest)
 * <p>
 * "thread -- t2" #13 prio=5 os_prio=0 tid=0x000000001783d800 nid=0x2500 runnable [0x000000001855f000]
 * java.lang.Thread.State: RUNNABLE
 * at aqs.JstackTest$2.run(JstackTest.java:63)
 * - locked <0x0000000740fc6238> (a java.lang.Class for aqs.ReentrantLockTest)
 * <p>
 * "thread -- t1" #12 prio=5 os_prio=0 tid=0x00000000177f5000 nid=0x3bd8 in Object.wait() [0x000000001845f000]
 * java.lang.Thread.State: WAITING (on object monitor)
 * at java.lang.Object.wait(Native Method)
 * - waiting on <0x0000000740fc6238> (a java.lang.Class for aqs.ReentrantLockTest)
 * at java.lang.Object.wait(Object.java:502)
 * at aqs.JstackTest$1.run(JstackTest.java:47)
 * - locked <0x0000000740fc6238> (a java.lang.Class for aqs.ReentrantLockTest)
 * <p>
 * <p>
 * "thread -- t6" #17 prio=5 os_prio=0 tid=0x00000000172fe000 nid=0x1714 waiting on condition [0x000000001844f000]
 * java.lang.Thread.State: TIMED_WAITING (parking)
 * at sun.misc.Unsafe.park(Native Method)
 * at java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:338)
 * at aqs.JstackTest$6.run(JstackTest.java:126)
 * <p>
 * "thread -- t5" #16 prio=5 os_prio=0 tid=0x00000000172f9800 nid=0x98 waiting on condition [0x000000001834f000]
 * java.lang.Thread.State: WAITING (parking)
 * at sun.misc.Unsafe.park(Native Method)
 * at java.util.concurrent.locks.LockSupport.park(LockSupport.java:304)
 * at aqs.JstackTest$5.run(JstackTest.java:118)
 *
 * @author mahao
 * @date 2022/06/12
 */
public class JstackTest {


    /**
     * 1. BLOCKED  /  waiting to lock <0x0000000740fc6238>  /  waiting for monitor entry
     * 2. RUNNABLE  /  - locked <0x0000000740fc6238>   /  runnable
     * 3. WAIT、WAITING / 先持有锁- locked <0x0000000740fc6238>，在 - waiting on <0x0000000740fc6238>   / in Object.wait()
     * 4. WAIT、WAITING / LockSupport不需要持有锁   / waiting on condition
     */
    @Test
    public static void main(String[] args) throws InterruptedException {
        //waiting状态，locked(ReentrantLockTest), waiting on <0x0000000740bac798>
        Thread t1 = new Thread("thread -- t1") {
            @Override
            public void run() {
                synchronized (ReentrantLockTest.class) {
                    try {
                        System.out.println("t1 获取锁后阻塞");
                        ReentrantLockTest.class.wait(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        Thread.sleep(1000);

        //Runnable状态: locke(ReentrantLockTest)
        Thread t2 = new Thread("thread -- t2") {
            @Override
            public void run() {
                synchronized (ReentrantLockTest.class) {
                    System.out.println("线程2 执行");
                    while (true) {
                    }
                }
            }
        };
        t2.start();
        Thread.sleep(1000);

        //block  waiting to lock <0x0000000740bac798>。
        Thread t3 = new Thread("thread -- t3") {
            @Override
            public void run() {
                synchronized (ReentrantLockTest.class) {
                    System.out.println("线程3 执行");
                    ThreadUtil.sleep(10000);
                }
            }
        };
        t3.start();

        //block  waiting to lock <0x0000000740bac798>。
        Thread t4 = new Thread("thread -- t4") {
            @Override
            public void run() {
                synchronized (ReentrantLockTest.class) {
                    System.out.println("线程4 执行");
                    ThreadUtil.sleep(10000);
                }
            }
        };
        t4.start();

        //block  waiting to lock <0x0000000740bac798>。
        Thread t5 = new Thread("thread -- t5") {
            @Override
            public void run() {
                LockSupport.park();
            }
        };
        t5.start();

        Thread t6 = new Thread("thread -- t6") {
            @Override
            public void run() {
                LockSupport.parkNanos(TimeUnit.MINUTES.toNanos(10));
            }
        };
        t6.start();


        t1.join();
        t2.join();
        System.out.println("main  结束 ----------");
    }
}
