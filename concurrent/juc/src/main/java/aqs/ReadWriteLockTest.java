package aqs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * 读写锁实现:
 *
 * @author mahao
 * @date 2022/06/13
 */
public class ReadWriteLockTest {

    ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "");


    @Test
    public void testUse() throws Exception {

        Thread t1 = new Thread(() -> {
            threadLocal.set("aaa");
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            threadLocal.set("aaa");
        });
        t2.start();


        Thread.sleep(3000);
        System.out.println(t1);

        threadLocal = null;

        System.out.println(t1);
        System.out.println(t2);

    }

    /**
     * 1. 如果当前线程持有写锁，则可以在对其加读锁，相反不可以。 此时该线程当释放写锁后，就退化为持有读锁了。
     */
    @Test
    public void testLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        //1. 当前线程先获取读锁后，在获取写锁，不行，会一直卡在写锁获取
//        lock.readLock().lock();
//        System.out.println("1. 获取读锁成功");

//        lock.writeLock().lock();
//        System.out.println("2. 获取写锁成功");

        //2. 先获取写锁，早获取读锁
        lock.writeLock().lock();
        System.out.println("1. 获取写锁成功");
        lock.readLock().lock();
        System.out.println("2. 获取读锁成功");

        System.out.println("允许线程加写锁的时候 再加读锁； 持有读锁不能加写锁。");


    }

}
