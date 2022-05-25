package basic.day04;

/**
 * 查看代码块的执行顺序
 *      
 * @author mahao 
 * @date 2018年12月22日 下午8:24:37
 */
public class Demo1 {
	
	private int a =1;
	//1.
	static {
		System.out.println("1. 静态代码块-----");
	}
	//2.
	{
		a=10;
		System.out.println("2. 构造代码块-----");
	}
	//3.
	public Demo1() {
		System.out.println("3. 构造方法------");
	}
	
	public static void main(String[] args) {
		Demo1 d = new Demo1();
		System.out.println(d.a);
	}
}
