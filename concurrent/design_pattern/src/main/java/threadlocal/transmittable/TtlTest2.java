package threadlocal.transmittable;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlCallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对普通的ThreadLocal实现增强.  也是通过拷贝做的。
 *
 * @author mahao
 * @date 2023/04/21
 */
public class TtlTest2 {


    static ExecutorService executorService = Executors.newFixedThreadPool(2);


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        executorService.submit(() -> {
        }).get();
        executorService.submit(() -> {
        }).get();

        //可以用ttl对ThreadLocal进行增强，使其实现线程池之间的拷贝
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        TransmittableThreadLocal.Transmitter.registerThreadLocal(threadLocal, parentValue -> parentValue);


        threadLocal.set("zs");

        Thread thread = new Thread(() -> {
            String s = threadLocal.get();
            System.out.println("新建线程中获取父线程的线程变量: 这儿获取不到，是因为threadLocal 不是可继承的，新建线程是不走TTL的增强的: " + s);

            threadLocal.remove();
        });

        thread.start();
        thread.join();

        // 这种也是会影响到父线程变量值的，也是浅拷贝。
        System.out.println("父线程中获取线程变量: " + threadLocal.get());


        // =========================
        TtlCallable<Object> callable = TtlCallable.get(() -> {
            String s = threadLocal.get();
            System.out.println("改造后，在线程池中获取threadlocal值能取到,是因为registerThreadLocal了,这个ThreadLocal就被拷贝子线程了：" + s);
            return null;
        });
        executorService.submit(callable).get();


        // 这种也是会影响到父线程变量值的，也是浅拷贝。
        System.out.println("父线程中获取线程变量: " + threadLocal.get());

        executorService.shutdown();
    }

}
