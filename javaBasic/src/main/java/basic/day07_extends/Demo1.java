package basic.day07_extends;

/**
 * final 关键字的使用
 * 
 * 1.修饰变量：则这个变量是一个常量，并且只能被赋值一次； 2.修饰方法：则这个方法不允许子类去复写；
 * 2.修饰方法：则这个方法不允许被子类覆盖；
 * 3.修饰类：则这个类不允许继承；
 * 4.内部类访问时，只能访问被final修饰的局部变量
 * 
 * @author mahao
 * @date 2019年1月5日 下午6:34:45
 */

class Demo1 extends Demo{

	/** 常量数据*/
	public static final int NUM = 3;
	
	
	
	public static void main(String[] args) {
		
		final String a = "l";
		
		/**
		 * 4内部类访问时，只能访问被final修饰的局部变量
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(a);
			}
		}).start();
	}
	
}

class Demo {
	
	/**被final修饰的方法不允许被覆盖*/
	public final void show1() {
		System.out.println("被final修饰的方法");
	}
	
	public void show2() {
		System.out.println("方法");
	}
}