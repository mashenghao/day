package basic.day02;

import org.junit.Test;

/**
 * 位运算符
 *
 * << >> >>> & | ^ ~
 * 
 * 左移 右移 无符号右移 与 或 异或 反码
 * 
 * @author mahao
 * @date: 2018年12月15日 下午6:41:25
 */
public class Demo3 {

	/**
	 * 左移，右移，无符号右移
	 */
	@Test
	public void test1() {
		int x = 6;
		System.out.println("(x*2(n))---x左移两位后的数据为： " + (x << 2));

		System.out.println("(x/2(n))---6右移两位后的数据为： " + (6 >> 2));

		System.out.println("(x/2(n))---6无符号右移两位后的数据为： " + (-6 >>> 1));

	}

	/**
	 * & | ^ ~ 运算符
	 */
	@Test
	public void test2() {
		System.out.println("5&2=0-----" + (5 & 2));

		System.out.println("5|2=7------" + (5 | 2));

		System.out.println("5^2=7------" + (5 ^ 2));
		System.out.println("5^2^2=5----" + (5 ^ 2 ^ 2));

		System.out.println("~5=" + (~5));
	}

	/**
	 * 最有效率的方式算出2乘以8等于几？
	 */
	@Test
	public void test3() {
		System.out.println(2 << 3);
	}

	/**
	 * 对两个整数变量的值进行互换(不需要第三方变量)
	 */
	@Test
	public void test4() {

		int m = 5, n = 3;
		//方法一
//		m = m + n;
//		n=m-n;
//		m=m-n;
//		System.out.println("m="+m+", n="+n);
		
		//方法二（异或方式）
		n=m^n;
		m=m^n;//(m^m^n=n)
		n=m^n;//(n^m^n=m)
		
		System.out.println("m="+m+", n="+n);	

	}

}
