package interview.lambda;

import java.io.Serializable;

/**
 * @author mahao
 * @date 2022/11/22
 */
@FunctionalInterface
public interface Apply<T, R> extends Serializable {

    R apply(T t);
}
