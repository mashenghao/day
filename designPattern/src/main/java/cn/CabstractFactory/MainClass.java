package cn.CabstractFactory;

/**
 * 03 抽象工厂方法模式
 * 
 * 案列：水果南北关系获取
 * 
 */
public class MainClass {
	
	/**
	 * 这里是用来
	 * 
	 */
	public static void main(String[] args) {
		FruitFactory ff = new NorthFactory();
		Fruit apple = ff.getApple();
		Fruit banana = ff.getBanana();
		apple.get();
		banana.get();
	}
}
