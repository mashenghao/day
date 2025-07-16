package interview.annotation;

import java.io.File;
import java.lang.annotation.Annotation;

/**
 * 1. 所有的注解都是继承了 Annotation的接口，所以注解的声明使用interface的声明的。
 * 2. 每次在一个地方声明一个注解，在class的字节码中就会出现这个注解名 与 注解的字段的每个值的value
 * 3. 获取注解时， AnnotationSource.class.getAnnotations(); 可以拿到注解的实例对象，实例对象是通过动态代理生成的。
 *
 * @author mash
 * @date 2025/5/6 下午11:38
 */
public class AnnotationSource {
    public static void main(String[] args) {
        Annotation[] annotations = AnnotationSource.class.getAnnotations();
    }
}
