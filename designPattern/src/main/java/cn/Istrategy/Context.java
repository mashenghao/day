package cn.Istrategy;

/**
 * 策略的调用，负责调用子类策略；
 */
public class Context {

	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public double sale(double num) {
		double num2 = this.strategy.cost(num);
		return num2;
	}

}
