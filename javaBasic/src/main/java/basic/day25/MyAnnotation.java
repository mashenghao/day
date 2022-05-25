package basic.day25;

/**
 * 自定义注解
 *
 * @author mahao
 * @date 2019年4月24日 下午1:38:33
 */
public @interface MyAnnotation {

	public long time() default 100;

}

