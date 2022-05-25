package cn.Lproxy.JDKProxy;
/**
 * 被代理方法具体实现类
 * 
 * @author  mahao
 * @date:  2018年10月27日 上午10:17:12
 */
public class MySubject implements Subject{

	@Override
	public void say(String name) {
		System.out.println(name+ "这是我");
	}
	
}
