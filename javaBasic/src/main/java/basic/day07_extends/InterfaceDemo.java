package basic.day07_extends;

/**
 * 接口中的属性都是被public static final 修饰，默认可以不写，默认是常量；
 * 接口中的方法都是被public abstract修饰，默认也可以不写；
 *      
 * @author mahao 
 * @date 2019年1月7日 下午2:37:00
 */
public interface InterfaceDemo {
	
	 int NUM=3;
	 
	 public static final int NUM_2=3;
	 
	 int add(int a ,int b );
}
