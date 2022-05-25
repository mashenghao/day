package basic.day02;

import java.text.DecimalFormat;

import org.junit.Test;

/**
 * 算数运算符
 *
 * @author  mahao
 * @date:  2018年12月15日 下午6:33:22
 */
public class Demo {
	
	/**
	 * 算数运算符，4/3都为整数，不会计算得double类型。
	 */
	@Test
	public void test1() {
		System.out.println(4/3);
		System.out.println(new DecimalFormat("#.00").format(15.00005));
	}
	
	/**
	 * ++  -- 理解
	 */
	@Test
	public void test2() {
		int a = 2 ,b;
		/*
		 * 1.在执行时，a先将值传递出去，在进行自身增加
		 * a++ = a=a+1;
		 */
	//	b=a++;  //b=2,a=3;
	//	System.out.println(a+"--"+b);
		/*
		 * 2.执行时，a进行++操作,没有接受返回值的，
		 * a自身增加，在增加的基础上进行+2操作，赋值给b
		 */
		b=(a++)+2;  //b=4,a=3;
		System.out.println(a+"--"+b);
		/*
		 *3.在执行时,a先将值自增，在传递数据 
		 */
//		b=++a;  //a=3;b=3
//		System.out.println(a+"--"+b);
	}
}
