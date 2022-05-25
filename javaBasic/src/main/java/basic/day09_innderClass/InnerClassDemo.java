package basic.day09_innderClass;

/**
 * 内部类的访问规则
 * 1.内部类可以直接访问外部类的成员，包括私有
 * 2.外部类访问内部类，必须建立内部类的对象
 * 3.之所以内部类可以访问外部类，因为内部类中，持有外部类的引用，
 * 			格式： 外部类名.this
 *
 * @author  mahao
 * @date:  2019年2月14日 下午1:10:55
 */
public class InnerClassDemo {
	
	public static void main(String[] args) {
		Out out = new Out(); 
		out.method();
		
		//直接获取内部类对象
		Out.Inner in = new Out().new Inner();
		in.function();
	}
}


/**
 * 外部类
 */
class Out{
	
	//外部类属性
	private String name = "外部类名字";
	
	/**
	 *内部类
	 */
	class Inner{
		//3.当内部类和外部类有相同属性时，要用this的使用
		private String name = "内部类名字";
		
		//1.内部类可以直接访问外部类成员
		public void function() {
			String name = "名字";
			
			System.out.println("不加this，默认访问方法里面的变量，如果没有，则搜索成员变量：---"+name);
			System.out.println("this在这里，是指Inner对象，所以指的是内部类属性：---"+this.name);
			System.out.println("内部类访问外部类属性，通过外部类名.this：---"+Out.this.name);


		}
	}
	
	//2.外部类访问内部类，必须先有内部类对象
	public void method() {
		Inner inner = new Inner();
		inner.function();
	}
	
	
}
