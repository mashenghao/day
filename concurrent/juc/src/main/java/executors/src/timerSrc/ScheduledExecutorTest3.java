package executors.src.timerSrc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试定时任务执行失败，就终止了
 *
 * @author mahao
 * @date 2022/12/30
 */
public class ScheduledExecutorTest3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        AtomicInteger atomicInteger = new AtomicInteger();
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            int andIncrement = atomicInteger.getAndIncrement();
            System.out.println(andIncrement);
            //当抛出异常时，scheduledFuture就结束了。
            if (andIncrement == 3) {
                throw new RuntimeException();
            }
        }, 1, 2, TimeUnit.SECONDS);

        Thread.sleep(2000);
        scheduledFuture.cancel(true);

        //只有scheduledFuture状态是结束了，get才有返回值，否则会一直陷入阻塞中，。 结束包括定时任务结束或者外部取消结束。
        scheduledFuture.get();
        scheduledExecutorService.shutdown();
    }
}
