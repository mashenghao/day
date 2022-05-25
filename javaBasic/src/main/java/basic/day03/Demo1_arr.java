package basic.day03;

import org.junit.Test;

/**
 * 	数组操作
 *      
 * @author mahao 
 * @date 2018年12月19日 下午8:49:09
 */
public class Demo1_arr {
	
	/**定义数组的三种方式*/
	@Test	
	public void demo1() {
		int[] arr1 = new int[3];
		int[] arr2 = {1,2,3};
		int[] arr3 = new int[] {};
		int[] arr4 = null;//数组必须初始化
		System.out.println(arr1[2]+"--"+arr2+"...."+arr3 + arr4);
	} 
	
	/** 数组为引用对象,改变了堆内存中的数据*/
	@Test	
	public void demo2() {
		int[] arr1 = new int[3];
		int[] arr2 = {1,2,3};
		int[] arr3 = new int[] {1,2,3};
		add(arr3);
		System.out.println(arr1+"--"+arr2[1]+"...."+arr3[1]);
	} 
	/**改变数据的方法*/
	public static void add(int[] arr) {
		arr[1]=12;
	}
	
	@Test
	public void demo3() {
		/*
		 * 基本数据类型不会发生改变，因为基本类型的数据
		 * 放在栈内存中，方法参数中的数据，是对这个数据的
		 * 一个拷贝，原来的数据依然是保持不变的。
		 */
		Integer x =  new Integer(2);
		change(x);
		System.out.println(x);//2
		/*
		 * 已经是对象了啥，会改变变数据
		 */
		StringBuffer  s = new StringBuffer("aaa");
		change2(s);
		System.out.println(s);//aaabbbb
	}
	 
	private void change2(StringBuffer s) {
		s.append("bbbb");
	}

	/**改变数据的方法*/
	public static void change(int a) {
		a=a*10;
	}
}
