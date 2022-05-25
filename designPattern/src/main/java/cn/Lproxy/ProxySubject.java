package cn.Lproxy;
/**
 * 手动创建代理类
 */
public class ProxySubject implements Subject{
	
	private Subject target;
	
	public ProxySubject(Subject target ){
		//传入被代理对象
		this.target=target;
	}
	
	@Override
	public void say(String name) {
		System.out.println("前置增强-----------");
		target.say(name);
		System.out.println("后置增强---------------");
	}
	
}
