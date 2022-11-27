package jReactor.multiReactor;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mahao
 * @date 2022/11/20
 */
public class ServerBootStrap {

    public Future<Void> bind(int port) throws IOException {

        ExecutorService bossThreadPool = Executors.newCachedThreadPool(new CustomThreadFactory("主Reactor-pool-"));
        ExecutorService workerThreadPool = Executors.newFixedThreadPool(2, new CustomThreadFactory("子Reactor-pool-"));

        ExecutorService businessPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new CustomThreadFactory("业务-pool-"));

        SubReactors subReactors = new SubReactors(workerThreadPool);
        MainReactor mainReactor = new MainReactor(subReactors, businessPool);
        CompletableFuture<Void> future = new CompletableFuture<>();
        bossThreadPool.execute(() -> {
            try {
                //死循环运行
                mainReactor.run();

                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        
        return future;
    }
}
