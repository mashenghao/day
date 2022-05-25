package cn.Esingleton.demo;

/**
 * 静态内部类实现单例设计模式
 *
 * @author mahao
 * @date 2019年4月24日 上午10:29:24
 */
public class ESingle3 {

	private ESingle3() {
	}

	private static class Inner {

		private static final 
		
		ESingle3 instance = new ESingle3();
	}

	public ESingle3 getInstance() {

		return Inner.instance;
	}

}
