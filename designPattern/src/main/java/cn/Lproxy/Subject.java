package cn.Lproxy;

/**
 * 代理和被代理类统一的方法规范接口
 */
public interface Subject {
	/**
	 * 被代理方法
	 */
	public void say(String name);
}
