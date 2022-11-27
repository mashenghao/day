package jReactor.multiReactor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mahao
 * @date 2022/11/26
 */
public class CustomThreadFactory implements ThreadFactory {

    private AtomicInteger id = new AtomicInteger(0);

    private final String prefix;

    public CustomThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + id.getAndIncrement());
    }
}
