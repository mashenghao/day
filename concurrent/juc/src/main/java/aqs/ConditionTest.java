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
 * ====== 重要==========
 * linux mutux 的实现， 陷入wait 通过是通过mutux提供的 pthread_cond_wait方法，作用是
 * 1. 先去释放锁，使其他线程可以去竞争
 * 2. 阻塞当前线程
 * 3. 当前线程被唤醒后，竞争获取锁
 *
 * void pthread_cond_wait(cond_t *cond, mutex_t *mutex) {
 *     // 1. 保存当前计数器值
 *     uint32_t old_counter = atomic_load(&cond->counter, memory_order_acquire);
 *
 *     // 2. 原子释放锁并进入等待
 *     atomic_unlock(mutex);
 *
 *     // 3. 调用 futex 阻塞（条件：cond->counter == old_counter）
 *     syscall(SYS_futex, &cond->data, FUTEX_WAIT_PRIVATE, old_counter, NULL);
 *
 *     // 4. 被唤醒后重新竞争锁
 *     pthread_mutex_lock(mutex);
 * }
 *
 *
 *
 void pthread_cond_signal(cond_t *cond) {
 // 1. 原子增加计数器
 atomic_fetch_add(&cond->counter, 1, memory_order_release);

 // 2. 唤醒一个等待者
 syscall(SYS_futex, &cond->data, FUTEX_WAKE_PRIVATE, 1);
 }
 *
 * ===============  java 的wait 实现===========
 * ​​操作系统原语：​​ 实现线程阻塞和唤醒需要底层操作系统的支持。在 Linux 上，JVM（主要是 HotSpot VM）主要使用以下机制：
 * ​​pthread_mutex_t 与 pthread_cond_t：​​ JVM 内部的 Monitor 对象在重量级锁状态下，其底层实现通常会封装 POSIX 线程库提供的互斥锁 (pthread_mutex_t) 和条件变量 (pthread_cond_t)。
 * pthread_mutex_lock() / pthread_mutex_unlock()： 用于实现锁的获取和释放。
 * pthread_cond_wait()： 当线程调用 Object.wait() 时，JVM 底层会调用此函数将线程放入 Monitor 的 Wait Set 并释放锁，线程进入阻塞状态。
 * pthread_cond_signal() / pthread_cond_broadcast()： 当线程调用 Object.notify() / notifyAll() 时，JVM 底层会调用这些函数来唤醒 Wait Set 中的一个或所有线程。
 *
 * --------------  java的park 暂停当前线程 与 mutux的wait实现思路一样--------
 * void Parker::park() {
 *     // 原子交换操作：将_counter设为0并返回旧值
 *     // 如果旧值>0，表示存在许可，直接返回  先用jvm内部结构，判断一下是否有许可证，也就时别人唤醒过，没有的话，才去操作系统的mutux。
 *     if (Atomic::xchg(0, &_counter) > 0)
 *         return;
 *
 *     // 获取操作系统的 mutux的锁，  加锁后再去调用wait
 *     pthread_mutex_lock(_mutex);        // 获取互斥锁
 *
 *     // 循环等待（防御虚假唤醒）
 *     while (_counter <= 0) {  //当被唤醒后， 判断count是否> 0,如果大于0了，则表示有别的地方掉过mutux的signal，然后将 count进行++。
 *         // 线程在此阻塞，释放_mutex锁
 *         pthread_cond_wait(_cond, _mutex);  //否则阻塞， 并去释放锁， 唤醒其他进程操作，操作完成后 调用unpark， 将count++， 这里就能用了。
 *     }
 *
 *     _counter = 0;                     // 消费许可
 *     pthread_mutex_unlock(_mutex);      // 释放互斥锁
 * }
 *
 * void Parker::unpark() {
 *     // 原子交换操作：设置_counter为1并返回旧值
 *     // 如果旧值==0，表示需要唤醒阻塞线程
 *     if (Atomic::xchg(1, &_counter) == 0) {
 *         pthread_mutex_lock(_mutex);     // 获取互斥锁
 *         pthread_cond_signal(_cond);     // 唤醒一个等待线程
 *         pthread_mutex_unlock(_mutex);   // 释放互斥锁
 *     }
 * }
 *
 *
 *
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
