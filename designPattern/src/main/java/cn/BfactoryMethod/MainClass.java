package cn.BfactoryMethod;
/**
 * 02 工厂方法模式
 * 	
 * 案列：水果关系
 * 
 *	1.建造抽象工厂，为具体工厂提供共有的父类
 *	2.创建具体工厂，负责实例的创建
 */
public class MainClass {
	
	/**
	 * 实现分析：
	 * 抽象工厂，FriutFactory将具体工厂抽象出来，抽象工厂那个用于
	 *	获取具体的具体实例工厂，实例的创建放在了具体工厂中。
	 *
	 */
	public static void main(String[] args) {
		//通过工厂，获取苹果实例
		FruitFactory ff = new AppleFactory();
		Fruit apple = ff.getFruit();
		apple.get();
		//获取梨子实例
		FruitFactory ff2 = new PearFactory();
		Fruit pear = ff2.getFruit();
		pear.get();
	}
}
