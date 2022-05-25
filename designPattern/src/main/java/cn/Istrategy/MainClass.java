package cn.Istrategy;

/**
 * 策略模式
 *
 * 类似于包装模式，不同的是，包装模式是包装的父类， 子类去调用，策略模式是，子类传入父类，让父类去调用。
 */
public class MainClass {

	public static void main(String[] args) {
		// 1.创建策略的实例
		StrategyA strategyA = new StrategyA();
		StrategyB strategyB = new StrategyB();
		//2.将策略实例，传入调用中
		Context c = new Context(strategyB);
		//3.得出结果
		System.out.println(c.sale(10));
	}
}
