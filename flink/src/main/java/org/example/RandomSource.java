package org.example;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mash
 * @date 2024/1/4 22:06
 */
public class RandomSource extends RichSourceFunction<String> {

    boolean cancel = false;
    AtomicLong atomicLong = new AtomicLong(0);

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (!cancel) {
            ctx.collect(atomicLong.incrementAndGet() + "");
            Stu.sleep(100);
            if (atomicLong.get() % 50 == 0) {
                Stu.sleep(10000);
            }
        }
    }

    @Override
    public void cancel() {
        cancel = true;
    }
}
