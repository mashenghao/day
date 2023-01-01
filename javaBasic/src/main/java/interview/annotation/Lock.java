package interview.annotation;

import java.lang.annotation.*;

/**
 * 元注解：
 * 1.@Retention: 注解作用范围，SOURCE CLASS RUNTIME三种作用范围
 * 2.@Target: 作用的位置，多选，TYPE FIELD PARAMETER PARAMETER
 * 3.@Inherited - 标记这个注解是可以被子类继承的。
 * 4.@Documented - 标记这个注解的方法，javaDoc中将会包括这个注解的展示。
 *
 * @author mahao
 * @date 2022/12/30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Lock {
}
