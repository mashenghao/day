package cn.JobServer.my;

import org.junit.Test;

/**
 *	自定义监视模式 
 *
 */
public class MainClass {
	
	@Test
	public  void demo1() {
		//1.被检查者
		MyObservable o = new MyObservable();
		//2.检查者
		MyObserver ob = new MyObserver();
		//3.检查者检查对象
		o.addObserver(ob);
		//4.执行
		o.setName("dfjnss");
	}
	
	@Test
	public void demo2() {
		//1.被检查者
		MyObservable o = new MyObservable();
		//2.检查者
		MyObserver ob = new MyObserver();
		//3.检查者检查对象
		o.addObserver(ob);
		//4.执行
		o.setName("dfjn222222222");
	}
	
}
