package basic.day25;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义junit，实现方法调用
 *
 * @author mahao
 * @date 2019年4月24日 下午4:46:16
 */
//用于确定被修饰的自定义注解的生命周期（Runtime（存在运行时，替代xml文件），class（提供jvm使用），source（提供给编译器使用））
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface MyJunit {
	
	public String param() default "default";
}
