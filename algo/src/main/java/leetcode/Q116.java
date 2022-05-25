package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * 假设有这么一个类：
 * <p>
 * class ZeroEvenOdd {
 *   public ZeroEvenOdd(int n) { ... }      // 构造函数
 * public void zero(printNumber) { ... }  // 仅打印出 0
 * public void even(printNumber) { ... }  // 仅打印出 偶数
 * public void odd(printNumber) { ... }   // 仅打印出 奇数
 * }
 * 相同的一个 ZeroEvenOdd 类实例将会传递给三个不同的线程：
 * <p>
 * 线程 A 将调用 zero()，它只输出 0 。
 * 线程 B 将调用 even()，它只输出偶数。
 * 线程 C 将调用 odd()，它只输出奇数。
 * 每个线程都有一个 printNumber 方法来输出一个整数。请修改给出的代码以输出整数序列 010203040506... ，其中序列的长度必须为 2n。
 * <p>
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-zero-even-odd
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author: mahao
 * @date: 2019/11/13
 */
public class Q116 {


    public static void main(String[] args) throws InterruptedException {

        final ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);
        List<Integer> list = new ArrayList<>(5 * 2);
        new Thread(() -> {
            try {
                zeroEvenOdd.zero((num) -> {
                    list.add(num);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd((num) -> {
                    list.add(num);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.even((num) -> {
                    list.add(num);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        System.out.println(list);

    }

}

class ZeroEvenOdd {


    Semaphore semaphore = new Semaphore(1);
    Semaphore semaphore1 = new Semaphore(0);
    Semaphore semaphore2 = new Semaphore(0);

    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; ) {
            semaphore.acquire();
            printNumber.accept(0);
            System.out.println("zero: " + 0);
            i++;
            if (i % 2 == 0) {
                semaphore2.release();
            } else {
                semaphore1.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; ) {
            semaphore2.acquire();
            printNumber.accept(i);
            System.out.println("even: " + i);
            i += 2;
            semaphore.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {//奇数
        for (int i = 1; i <= n; ) {
            semaphore1.acquire();
            printNumber.accept(i);
            System.out.println("odd: " + i);
            i += 2;
            semaphore.release();
        }
    }
}