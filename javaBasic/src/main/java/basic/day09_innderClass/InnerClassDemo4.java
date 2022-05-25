package basic.day09_innderClass;

/**
 * 匿名内部类
 * 
 * 1.匿名内部类是对内部类的简写形式<br>
 * 2.内部类必须有接口或者父类
 *
 * @author mahao
 * @date: 2019年2月14日 下午7:39:22
 */
public class InnerClassDemo4 {

	public static void main(String[] args) {
		Out4 out = new Out4();
		out.function2();
	}

}

interface IN {
	void method();
}

/** 外部类 */
class Out4 {

	/** 内部类 */
	class Inner implements IN {

		@Override
		public void method() {
			System.out.println("方法执行......");
		}
	}

	// 外部类成员变量方法
	void function() {
		Inner in = new Inner();
		in.method();
	}

	// 用匿名内部类简写为
	void function2() {
		// Inner in = new Inner();
		// in.method();
		/**
		 * 匿名内部类的格式
		 */
		new IN() {

			@Override
			public void method() {
				System.out.println("方法执行2......");
			}

		}.method();
	}
}