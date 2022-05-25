package cn.Lproxy.JDKProxy;

import java.lang.reflect.Proxy;

/**
 * JDK的动态代理
 * 
 * @author  mahao
 * @date:  2018年10月27日 上午10:25:16
 */
public class MainClass {
	
	public static void main(String[] args) {
		// 1.被代理实例
		MySubject sub = new MySubject();
		//2.创建被代理实现处理类，并将被代理对象传入代理类中
		MyInvocationHandler handler = new MyInvocationHandler(sub);
		//3.得到代理
		Subject proxy = (Subject) handler.getProxy();
		//或者
		proxy = (Subject) Proxy.newProxyInstance(sub.getClass().getClassLoader(), sub.getClass().getInterfaces(), handler);
		//4.调用
		proxy.say("maha");
	}
}
