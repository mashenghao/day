package basic.day04;

/**
 * this super 关键字的使用
 *      
 * @author mahao 
 * @date 2018年12月22日 下午8:25:00
 */
public class Demo2 extends Demo2Parent{
	
	private String name;
	 
	public Demo2() {
		super();
		System.out.println("无参数的构造方法");
	}
	
	public Demo2 (String name) {
		/**
		 * 调用构造方法，必须放在第一行
		 */
		this();
		this.name=name;
		System.out.println("执行含参数的构造方法--");
	}
	
	public static void main(String[] args) {
		new Demo2("mahaoi");
		/*父类构造方法
		无参数的构造方法
		执行含参数的构造方法--*/
	}
}
