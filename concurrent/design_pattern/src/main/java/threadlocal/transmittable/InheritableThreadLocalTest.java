package threadlocal.transmittable;

import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试 java 自己对threadLocal 变量继承的实现类。
 *
 * @author mahao
 * @date 2023/04/21
 */
public class InheritableThreadLocalTest {

    static ExecutorService executorService = Executors.newFixedThreadPool(1);
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 让核心线程先去创建， 不然又是要继承。
        executorService.submit(() -> {
        }).get();
        executorService.submit(() -> {
        }).get();

        InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

        threadLocal.set("我是" + Thread.currentThread().getName());

        Thread thread = new Thread(() -> {
            String s = threadLocal.get();
            System.out.println("在子线程中获取到 threadLocal的值是:" + s);

            //如果子线程中移除threadLocal中的值， 子线程变量会被移除，当时父线程还是会保留的。
            threadLocal.remove();
            System.out.println("在子线程中二次获取 threadLocal的值是:" + threadLocal.get());

        });
        thread.start();
        thread.join();

        //子线程是对父线程中的inheritableThreadLocals 变量map 进行浅拷贝， 子进程修改map中k v 不会
        //影响父线程中的map，  但是会影响v的值， 因为是同一个对象。
        System.out.println("在主线程中获取 threadLocal的值是:" + threadLocal.get());


        // 线程池 中就无法实现了；。；；；；；；；；
        executorService
                .submit(() -> {
                    System.out.println("在线程池中获取到 threadLocal的值是无法获取的。:" + threadLocal.get());
                })
                .get();

        executorService.shutdown();
    }
}
