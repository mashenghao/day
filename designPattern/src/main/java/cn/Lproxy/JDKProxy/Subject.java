package cn.Lproxy.JDKProxy;

/**
 * 代理和被代理类统一的方法规范接口
 * 
 * @author  mahao
 * @date:  2018年10月27日 上午10:16:10
 */
public interface Subject {
	
	/**
	 * 被代理方法
	 * 
	 * @param name
	 */
	public void say(String name);
}
