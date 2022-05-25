package basic.day05_class;
/**
 * 
 * 类名：单例模式 懒汉式
 * 
 * 保证数据的唯一性，只有内存中只有一个实例
 * 
 * 要求：1.构造方法私有化
 * 		2.实例对象，为静态
 */
public class Single1 {
	/*
	 *1.构造方法私有，保证只能在类内部生成实例 
	 */
	private Single1(){};
	
	/*
	 *2.生成实例，为静态实例，在内存的方法区存储了这个实例。 
	 *	其余的得到实例，将会在方法区中得到方法区中实例的地址值
//	 *所以，只有一个实例；
	 */
	private static Single1 s = new Single1();
	
	public static Single1 getInstance(){
		return s;
	}
}

