package threadlocal.transmittable;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlCopier;
import com.alibaba.ttl.TtlRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阿里提供的支持可继承的threadLocal。
 * 可以实现线程池中的线程访问到父线程中的threadLocal变量。
 * <p>
 * 1. holder 也是个ThreadLocal是因为如果同一个threadLocal 本地线程没有设置值，  就不需要存储这个threadLoca值了， 也就是不需要将别的线程thradLcoa的值设置当前线程中。
 * <p>
 * 2.TransmittableThreadLocal 继承自InheritableThreadLocal，所以向实例设置的值，都是存在Thread 的inheritableThreadLocals属性中，新建线程无需任何操作
 * 都可以实现子线程继承父线程变量。
 * <p>
 * 3. 对于线程池访问，需要对runnable进行包装。 具体实现是：
 * submit的callable是包装后的runnable. 构造函数中将父线程(当前线程)使用过 TransmittableThreadLocal对应的值全部取出来，放到包装的runnable的成员变量，
 * 在run之前，将值重新放到threadLocalMap中，完成了跨线程的实现。
 * <p>
 * 4. 如何实现将父线程( **当前线程** ) **使用过** TransmittableThreadLocal对应的值 **全部**  取出来？
 * 实现是通过TransmittableThreadLocal 内部的一个静态ThreadLocal变量holder（是个map）。
 * TransmittableThreadLocal重写了 set方法，当调用完父类的set方法后， 会将这个TransmittableThreadLocal放到 holder中，表示 在当前线程中，使用
 * 过这个local实例, 这个local实例应该拷贝给子线程。
 * <p>
 * 5. 因为holder的map存储的是所有的TransmittableThreadLocal实例， 所以runnable的构造函数可以先去取到holder对应map中所有使用的ttl实例， 然后取来每个ttl实例
 * 对应的值，存到一个临时map中《k是ttl，value是对应的值》。 在run方法之前，进行设置值的操作。
 * <p>
 * 子线程结束后，会对线程进行恢复，
 * 这样子既起到了显式清除主线程带来的上下文，也避免了如果线程池的拒绝策略为 CallerRunsPolicy ，后续处理时上下文丢失的问题。
 *
 * @author mahao
 * @date 2023/04/21
 */
public class TtlTest {

    static ExecutorService executorService = Executors.newFixedThreadPool(2);


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        executorService.submit(() -> {
        }).get();
        executorService.submit(() -> {
        }).get();

        TransmittableThreadLocal<List<String>> threadLocal1 = new TransmittableThreadLocal<>();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        threadLocal1.set(list);

        Thread thread = new Thread(() -> {
            List<String> list1 = threadLocal1.get();
            System.out.println("新建线程中获取父线程的线程变量: " + list1);

            list1.add("3");

            threadLocal1.remove();
        });

        thread.start();
        thread.join();

        // 这种也是会影响到父线程变量值的，也是浅拷贝。
        System.out.println("父线程中获取线程变量: " + threadLocal1.get());


        // =========================
        TtlRunnable.get(()->{});
        TtlCallable<Object> callable = TtlCallable.get(() -> {
            List<String> list1 = threadLocal1.get();
            System.out.println("改造后，在线程池中获取threadlocal值能取到：" + list1);
            list1.add("4");
            return null;
        });
        executorService.submit(callable).get();

        // 这种也是会影响到父线程变量值的，也是浅拷贝。
        System.out.println("父线程中获取线程变量: " + threadLocal1.get());

        executorService.shutdown();
    }
}
