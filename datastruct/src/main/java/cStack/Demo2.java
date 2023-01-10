package cStack;

import org.junit.Test;

/**
 * 递归练习
 *
 * @author mahao
 * @date: 2019年3月20日 下午9:12:29
 */
public class Demo2 {

	@Test
	public void demo2() {
		int result = sum(100);
		out(result);
	}

	private int sum(int n) {
		if (n == 1)
			return 1;
		return n + sum(n - 1);

	}

	/************************************************************/
	@Test // 阶乘 5*4*3*2*1
	public void demo1() {
		int result = fib(5);
		out(result);
	}

	public int factorial(int n) {
		if (n == 1)
			return 1;
		int s = n * factorial(n - 1);
		return s;
	}

	int fib(int n) {
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else {
			int s = fib(n - 1);
			out(s);
			int s2 = s + fib(n - 2);
			return s2;
		}
	}

	static void out(Object obj) {
		System.out.println("" + obj);
	}
}
