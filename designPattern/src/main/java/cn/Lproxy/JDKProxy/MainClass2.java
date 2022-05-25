package cn.Lproxy.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理二次许学习理解；
 * 
 * 动态代理实现MySubject的增强：
 * 	jdk的动态代理是以前需要手动用代理模式书写一个代理类，现在是jdk自动
 * 	完成代理类的创建，jdk创建代理好的字节码文件，并且将实例对象通过Proxy.newProxyInstance（）
 * 	创建出来，对每个方法，都会在 InvocationHandler()的未实现方法中触发，所以对需要增强的方法
 * 可以进行增强。
 *
 * @author mahao
 * @date 2019年4月24日 下午6:21:46
 */
public class MainClass2 {

	public static void main(String[] args) {
		Subject on = new MySubject();
		Subject proxy = new MainClass2().getProxy(on);
		System.out.println(on);
		System.out.println(proxy);
		System.out.println(on==proxy);
		proxy.say("nihao");
	}
	
	
	public Subject getProxy(Subject subject) {
		return (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(),subject.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				if("say".equals(method.getName())) {
					System.out.println("对接口中的某个方法进行了增强");
					return method.invoke(subject, args);
				}
				//不需要增强的方法，正常执行
				return method.invoke(subject, args);
			}
		});
	}
}
