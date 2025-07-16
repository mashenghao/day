package rx;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author mash
 * @date 2025/2/24 上午4:23
 */
public class CMap {

    public static void main(String[] args) {

        Flux.just(1, 2, 3, 4, 5)
                .map(e -> e * 2)
                .doOnNext(e -> System.out.println(e))
                .doOnSubscribe(e -> {
                    System.out.println(e);
                    e.request(1);
                    e.cancel();
                })
                .subscribe(System.out::println);
    }
}
