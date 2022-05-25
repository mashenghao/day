package cn.CabstractFactory;

/**
 * 得到北方的产品，这个是面向产品的纵向，为了获取一类，具有相同特征的产品。
 * 
 * 工厂方法模式是获取结构对象使用。
 * 
 */
public class NorthFactory implements FruitFactory{

	public Fruit getApple() {
		return new NorthApple();
	}

	public Fruit getBanana() {
		return new NorthBanana();
	}

}
