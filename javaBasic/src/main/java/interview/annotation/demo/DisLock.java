package interview.annotation.demo;

import java.lang.annotation.*;

/**
 * 分布式锁注解
 * <p>
 * 作用与方法，提供注解处理器，处理注解。
 * <p>
 * lockName + lockKey  组成lockId。 lockName  和 lockKey 必须有一个不为空。
 * 添加key参数是为了更细粒度的锁。
 * <p>
 * {@link cn.com.bsfit.rgas.common.cache.DisLock#tryLock(String lockId)}的lockId = {@link DisLock#lockName()}  + {@link DisLock#lockKey()}
 *
 * @author mahao
 * @date 2022/12/30
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DisLock {

    /**
     * 锁的名字 lockName + lockKey 组成分布式锁的 lockId
     */
    String lockName() default "";

    /**
     * SpEL表达式  lockName + lockKey 组成分布式锁的 lockId
     */
    String lockKey() default "";

    /**
     * 失效时间，默认30s
     */
    int expireTime() default 30;


    /**
     * 未获取到分布式锁的时候，默认的处理策略
     */
    Policy policy() default Policy.IGNORE;


}
