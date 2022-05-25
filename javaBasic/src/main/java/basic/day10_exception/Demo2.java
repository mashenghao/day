package basic.day10_exception;

/**
 * 练习1 ：补足代码，调用两个函数，要求使用内部匿名类
 *
 *	复习：内部类使用
 *1.内部类创建在类的里面，有两个位置，函数内部和函数外
 *	函数外部是成员变量位置，则可以被修饰符修饰，保存static
 *	函数内的位置，访问外部类的属性可以直接访问，因为还持有外部类引用，
 *	访问方法内的局部变量是，该变量必须是被final修饰的
 *2.内部类可以直接访问外部类成员变量与方法，因为内部类持有
 *	外部类的引用，获取通过外部类名.this；
 *3.外部类获取内部类的数据，必须先创建对象
 *4.静态内部类的初始化可以通过 外部类.内部类 对象名 = new 外部类.内部类（）；
 *	静态内部类只可以访问外部类的静态属性
 *
 *
 * @author  mahao
 * @date:  2019年2月27日 下午6:23:08
 */
public class Demo2 {
	
	//方式三，内部类创建在成员变量上
	class InnerInter2 implements Inter{

		@Override
		public void show() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void fun() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(String[] args) {
		
		//方法二：使用在函数内部创建内部类
		class InnerInter implements Inter{

			@Override
			public void show() {
				System.out.println("方法二-----方法1");
			}

			@Override
			public void fun() {
				System.out.println("方法二-----方法2");	
			}
		}
		InnerInter ii = new InnerInter();
		ii.show();
		ii.fun();
		
///////////////////////////////////////////////////////////		
		
		//方式一：使用内部匿名类
		Inter i =new Inter() {//内部匿名类
			
			@Override
			public void show() {
				System.out.println("方法一-----方法1");
			}
			
			@Override
			public void fun() {
				System.out.println("方法一-----方法2");
			}
		};
		
		i.show();
		i.fun();
	}
}

interface Inter{
	void show();
	void fun();
}