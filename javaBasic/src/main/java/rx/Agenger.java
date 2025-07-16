package rx;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongConsumer;

/**
 * flux 创建方案：
 *
 * @author mash
 * @date 2025/2/24 上午1:37
 */
public class Agenger {

    public static void main(String[] args) throws Exception {

        //1. just 指定数据
        Flux.just("a", "b", "c", "d", "e", "f")//Flux创建一个FluxArray的Flux
                .subscribe(System.out::println); //订阅并消费。  FluxArray会创建一个ArraySubscription去遍历处理FluxArray中的数据，调用Subscriber的onNext方法。

        Disposable subscribe1 = Flux.just("a", "b", "c", "d", "e", "f")
                .filter(e -> e.endsWith("aa")) //过滤的filter。 对于上游他是消费者，对于下游是生产者，包装器模式。
                .subscribe(System.out::println);
        subscribe1.dispose();//实际是调用的ArraySubscription的cancel方法
        /*
            流程是：
            1. Publisher 可以接收 Subscriber的 订阅onSubscribe
            2. 订阅后产生一个Subscription，去处理Subscriber的执行
            3.Subscriber调用Subscription的request方法后， 品证就开始处理数据调用onNext方法了
            4.如果要去取消，
         */


        Flux.just("a", "b", "c", "d", "e", "f").subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
                s.cancel();
            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {
                //todo: 一次失败， 其他的方法都不会去调用了。
            }

            @Override
            public void onComplete() {

            }
        });

        //2.from方法组
//        Flux.fromIterable(List.of(1, 2, 3, 4, 5)).subscribe(System.out::println);


        //3.range
        System.out.println("////range");
        Flux.range(1, 10).subscribe(
                (e) -> System.out.println("消费" + e),
                (e) -> e.printStackTrace(),
                () -> System.out.println("完成任务")
        );

        System.out.println("////interval");
        //4.interval ,5秒后结束
        Disposable subscribe = Flux.interval(Duration.ofSeconds(2))
                .subscribe((e) -> System.out.println(Thread.currentThread().getName() + "  interval " + e));
        Thread.sleep(5000);
        subscribe.dispose();

        //5.自动完成任务
        System.out.println("////empty error never");
        Flux.never().subscribe(
                (e) -> System.out.println(Thread.currentThread().getName() + "  消费" + e),
                (e) -> e.printStackTrace(),
                () -> System.out.println(Thread.currentThread().getName() + "  完成任务empty")
        );

        //6、动态方法创建
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println("////  generate");
        Flux.generate(() -> 1, (sum, sink) -> {
            System.out.println(Thread.currentThread().getName() + "generate:  " + sum);
            int value = atomicInteger.incrementAndGet();
            sum += value;
            sink.next(sum);  //传给订阅者
            if (sum > 5) {
                sink.complete();
            }
            return sum; //传给下一个数据
        }).subscribe((e) -> {
            System.out.println(Thread.currentThread().getName() + " gen-sub " + e);
        });


        /**
         * reactor.core.publisher.FluxCreate#subscribe(reactor.core.CoreSubscriber);
         *
         * 这种方法是 Flux#subscribe 调用 --> publisher去回调Subscriber#onSubscribe方法，入参BufferAsyncSink -->
         * Subscriber调用BufferAsyncSink的request方法  -->  request方法消费取出队列中数据（首次为空）（此时是，拉数据） ---> onSubscribe方法执行完了
         * ----> 然后调用用户传进来的create方法函数  ---> 函数往sink.next扔数据，内部就取出Subscriber去调用next方法（此时是，推数据）。
         */
        //7. 动态方法创建 同时有pull 和 push
        Flux.create((sink) -> {
            System.out.println(Thread.currentThread().getName() + "  create");
            for (int i = 0; i < 10; i++) {
//                Executors.newCachedThreadPool().execute(() -> {
                sink.next(1); //offer 队列后  又立即 drain排除调用Subscriber的next方法。 推数据了。
//                });
            }

            //todo： sink支持回调 用于sink的回收  或者 初始化工作
            sink.onRequest(new LongConsumer() {
                @Override
                public void accept(long value) {

                }
            });
            //用于sink的回收  或者 初始化工作
//          sink.onDispose()

            sink.complete();
        }).subscribe((e) -> { //拉数据，调用request方法，当时Subscription 没有数据。
            System.out.println(Thread.currentThread().getName() + " e " + e);
        }); //create 方案调用后，会去调用subscribe循环调用的。

        Thread.sleep(1000000);
    }
}
