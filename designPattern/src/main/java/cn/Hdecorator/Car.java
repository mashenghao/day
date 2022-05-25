package cn.Hdecorator;

/**
 * 被装饰者的基类，所有的装饰，都要是他的子类，
 * 
 */
public class Car {

	public void run(){
		System.out.println("汽车能跑----");
	}
	
	/**
	 * 包装show方法，让这个方法显示car，所具有的功能
	 */
	public void show(){
		this.run();
	}
}
