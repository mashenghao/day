package cn.Esingleton;

import org.junit.Test;

public class MainClass {

	@Test
	public void demo1() {
		ESingle e1 = ESingle.getESinge();
		System.out.println(e1.toString());
		ESingle e2 = ESingle.getESinge();
		System.out.println(e2.toString());
	}

	/**
	 * 懒汉式加锁保证同步
	 * 
	 * @author mahao
	 */
	@Test
	public void demo2() {
		 Runnable r =  new Runnable() {
			public void run() {
				LSingle e1 = LSingle.getLSingle();
				System.out.println(e1.toString());
			}
		};
		
		new Thread(r).start();
		new Thread(r).start();
	}

}
