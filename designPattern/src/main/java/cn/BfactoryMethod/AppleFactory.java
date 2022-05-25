package cn.BfactoryMethod;

/**
 * 苹果的具体实现工厂
 */
public class AppleFactory extends FruitFactory{

	public Fruit getFruit() {
		return new Apple();
	}

}
