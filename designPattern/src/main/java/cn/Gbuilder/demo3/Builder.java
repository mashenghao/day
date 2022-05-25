package cn.Gbuilder.demo3;

/**
 * 抽象构建者--技术主管
 *
 * @author mahao
 * @date 2019年4月17日 下午12:42:23
 */
public abstract class Builder {

	public abstract Builder setName(String name);

	public abstract Builder setFunction(String function);

	public abstract Builder setSystem(String system);

	public abstract App build();

}
