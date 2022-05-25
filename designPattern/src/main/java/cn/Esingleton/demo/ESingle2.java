package cn.Esingleton.demo;

/**
 * 饿汉式变种实现单例设计
 *
 * @author mahao
 * @date 2019年4月24日 上午10:29:24
 */
public class ESingle2 {

	private static ESingle2 instance = null;

	private ESingle2() {
	}

	static {
		instance = new ESingle2();
	}

	public ESingle2 getInstance() {
		
		return instance;
	}

}
