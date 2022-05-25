package basic.day09_innderClass;

/**
 ******** 内部类的访问规则
 * 1.内部类可以直接访问外部类的成员，包括私有
 * 2.外部类访问内部类，必须建立内部类的对象
 * 3.之所以内部类可以访问外部类，因为内部类中，持有外部类的引用，
 * 			格式： 外部类名.this
 *
 *****静态内部类访问规则
 *	1.当内部类位于成员变量是时，可以被修饰符修饰，比如内部类私有
 *	2.当内部类被static修饰时，访问外部类的成员必须也是静态的，因为静态内部类先于对象存在，所以必须访问静态的
 *	3.访问静态内部类中的非静态成员，（获取静态内部类对象）
 *		Out.Inner in = new Out.Inner();
 *	4.外部类访问静态内部类中的成员, 内部类名.静态成员属性
 *
 *总结（2019/07/31）：对于静态内部类，就相当于创建了一个新的类，内部类与外部类
 *	没有什么关系，这时候静态内部类就是一个普通的类，访问外部类的属性和方法，必须先创建
 *	对象。
 *
 * @author  mahao
 * @date:  2019年2月14日 下午1:10:55
 */

/**
 * 外部类  cesi 
 */
class Out1{
	
	static String name = "外部类属性";
	
	String fiel = "普通属性";
	
	//外部类访问静态内部类中的非静态属性
	public void method() {
		System.out.println("外部类访问静态内部类中的静态成员----"+Inner.name);
	}
	
	
	/**
	 *内部类
	 */
	 static class Inner{
		
		static String name = "内部类属性";
		
		public void function() {
			System.out.println("静态内部类只能访问外部静态成员变量------"+Out1.name);
			System.out.println("静态内部类访问外部类普通成员变量-------"+new Out1().fiel);
		}
	}
	
}


public class InnerClassDemo2 {
	
	public static void main(String[] args) {
		//获取静态内部类中的对象
		Out1.Inner in = new Out1.Inner();
		in.function();
		
		Out1 out = new Out1();
		out.method();
	}
	
}

