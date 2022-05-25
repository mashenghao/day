package discuss;


/**
 * 三个线程如何实现交替打印ABC
 */
public class PrintAbc implements Runnable {

    private Object prev;
    private Object self;
    private char ch;

    public PrintAbc(Object prev, Object self, char ch) {
        this.prev = prev;
        this.self = self;
        this.ch = ch;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {//获取前置锁对象
                synchronized (self) {//获取当前对象锁
                    System.out.println(Thread.currentThread().getName() + "  " + ch);
                    count--;
                    self.notifyAll();
                }
                try {
                    if (count == 0) {
                        prev.notifyAll();
                    } else {
                        prev.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        PrintAbc p1 = new PrintAbc(c, a, 'A');
        PrintAbc p2 = new PrintAbc(a, b, 'B');
        PrintAbc p3 = new PrintAbc(b, c, 'C');
        new Thread(p1).start();
        Thread.sleep(10);// 保证初始ABC的启动顺序
        new Thread(p2).start();
        Thread.sleep(10);
        new Thread(p3).start();
        Thread.sleep(10);

    }

}
