package cn.Lproxy.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK实现动态代理后，在这里执行增强的方法
 * 
 * @author  mahao
 * @date:  2018年10月27日 上午10:26:41
 */
public class MyInvocationHandler implements InvocationHandler {
	
	//目标增强对象
	private Object target; 
	
	//构造方法，传入目标增强对象
	public MyInvocationHandler(Object target){
		this.target=target;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		
		System.out.println("前置增强-------------"+target==proxy);
		result = method.invoke(target, args);
		System.out.println("后置增强----------------");
		
		return result;
	}
	
	/**
	 * 得到代理对象
	 * @return 
	 */
	public Object getProxy(){
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this);
	}
}
