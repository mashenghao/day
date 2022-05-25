package cn.Obridge;

/**
 * 桥接的一段的父类
 * 
 * @author  mahao
 * @date:  2018年10月30日 下午1:26:51
 */
public abstract class Car {
	
	/**
	 * 这是桥接的位置，在父类中，持有Engine的引用
	 */
	protected Engine engine;
	
	public Car(Engine engine){
		this.engine=engine;
	}
	
	/**
	 * 组装不同的车，并给他们安装不同规格的引擎
	 */
	public abstract void makeCar();
	
}
