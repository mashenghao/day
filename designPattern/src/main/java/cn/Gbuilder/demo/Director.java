package cn.Gbuilder.demo;

/**
 * 导演类： 避免高层模块深入到建造者模式内部的实现类
 *
 * @author mahao
 * @date 2019年4月17日 上午10:57:30
 */
public class Director {

	private Builder builder = new BaseBuilder();

	public Product create() {
		builder.setPart();
		return builder.build();
	}
}
