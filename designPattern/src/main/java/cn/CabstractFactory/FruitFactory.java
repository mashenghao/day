package cn.CabstractFactory;
/**
 * 工厂接口,纵向的，得到共有的某个特征的产品
 */
public interface FruitFactory {
	
	public Fruit getApple();
	
	public Fruit getBanana();
	
}
