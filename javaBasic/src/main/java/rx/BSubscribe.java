package rx;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 订阅者
 *
 * @author mash
 * @date 2025/2/24 上午4:08
 */
public class BSubscribe {
    public static void main(String[] args) throws Exception {
        Disposable subscribe = Flux.just("1", "2", "3", "4", "5", "6", "7", "8", "9")
                .subscribe(
                        (e) -> {
                            System.out.println(e);
                        },
                        e -> e.printStackTrace(),
                        () -> {
                        },
                        (subscription) -> {
                            Subscription subscription1 = subscription;
                            subscription.request(10); //什么时候去发布者去取消息。
                            System.out.println();
                        }
                );

        System.out.println("--------------------");
        System.out.println("--------------------");

        Flux.just(1, 2)
                .subscribe(new Subscriber<Integer>() {
                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("onSubscribe");
                        subscription = s;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                        //处理完一个处理下一个。
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });


        System.out.println("--------------------");
        System.out.println("--------------------");
        Flux.just("aa", "bb")
                .concatWith(Mono.error(new IllegalStateException()))//错误不会中断的。
                .onErrorReturn("111")
                .subscribe(System.out::println);

        System.out.println("-----------多个订阅---------");
        Flux<Integer> just = Flux.just(1, 2, 3);
        just.subscribe(e -> System.out.println("1  " + e));
        just.subscribe(e -> System.out.println("2  " + e));

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        interval.subscribe(e -> System.out.println(Thread.currentThread().getName() + " -  3  " + e));
        interval.subscribe(e -> System.out.println(Thread.currentThread().getName() + " - 4  " + e));

        Thread.sleep(10000);


        //
        System.out.println("-----------多个订阅，通过使用request---------");
        Flux<Integer> just1 = Flux.just(1, 2, 3);

        just1.subscribe(new CoreSubscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(Thread.currentThread().getName() + "  6  " + integer);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        just1.subscribe(source -> System.out.println(Thread.currentThread().getName() + " 5  " + source));
    }


}
