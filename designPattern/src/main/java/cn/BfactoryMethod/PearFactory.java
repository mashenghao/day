package cn.BfactoryMethod;

/**
 * 梨子的具体实现工厂
 * */
public class PearFactory extends FruitFactory{

	public Fruit getFruit() {
		return new Pear();
	}

}
