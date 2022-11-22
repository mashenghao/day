package interview.lambda;

/**
 * @author mahao
 * @date 2022/11/22
 */
@FunctionalInterface
public interface Supply<R> {

    R supply();
}
