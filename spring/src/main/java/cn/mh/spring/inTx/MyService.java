package cn.mh.spring.inTx;

import org.springframework.transaction.annotation.Transactional;

/**
 * 实测发现,无论是在父接口 还是 父类方法上声明@Transactional注解， 子类实现这个方法都会开启事务的。
 * 原因是, 事务拦截器查询这个方法需不要开启事务，扫描注册的范围是 父类与父接口都会去扫描。
 *
 * org.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource#computeTransactionAttribute(java.lang.reflect.Method, java.lang.Class)
 *
 * org.springframework.transaction.annotation.AnnotationTransactionAttributeSource#findTransactionAttribute(java.lang.reflect.Method)
 *
 *org.springframework.transaction.annotation.SpringTransactionAnnotationParser#parseTransactionAnnotation(java.lang.reflect.AnnotatedElement)
 *
 *	private static MergedAnnotations findAnnotations(AnnotatedElement element) {
 * 		return MergedAnnotations.from(element, SearchStrategy.TYPE_HIERARCHY, RepeatableContainers.none());
 * 	    }
 *
 *
 * 扫描策略是SearchStrategy.TYPE_HIERARCHY : 		 * Perform a full search of the entire type hierarchy, including
 * 		 * superclasses and implemented interfaces.
 * @author mash
 * @date 2024/6/20 下午2:13
 */
public class MyService {

    @Transactional
    public void performTransactionalOperation() {

    }
}
