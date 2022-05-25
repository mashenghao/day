package cn.Gbuilder.demo;

/**
 * 创建构建者的抽象类<br>
 * 
 * 如果有多个产品，则有多个具体的构建者
 *
 * @author mahao
 * @date 2019年4月17日 上午10:51:02
 */
public abstract class Builder {
	
	//设定每个具体构建者的构建规则和构建顺序
	public abstract void setPart();
	
	//构建产品
	public abstract Product build();
}
