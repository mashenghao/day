package basic.day09_innderClass;

/**
 *内部类不在成员属性上，内部类定义在局部时，即定义在方法里面
 *
 *	1.不可以被成员修饰符修饰
 *	2.内部类可以直接访问外部类中的成员变量，因为还持有外部类中的引用
 *		但是访问方法里面的变量，只能被final修饰才可以
 */

/**
 * 外部类
 */
class Out2{
	
	private String name = "外部类属性";
	 
	public void method() {
		
		final int x =3;
		
		/**
		 *定义在方法上的内部类
		 */
		  class Inner{
			
			public void function() {
				//1.內部類可以訪問外部類的屬性
				System.out.println(name);
				//2.内部类访问的局部变量必须被final修饰
				System.out.println(x);
			}
			
		}
		  //方法里面创建内部类，并调用里面的方法
		new Inner().function();
	}
	
	
}


public class InnerClassDemo3 {
	
	public static void main(String[] args) {
		Out2 out = new Out2();
		out.method();
	}
	
}