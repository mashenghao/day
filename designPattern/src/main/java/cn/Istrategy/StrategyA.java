package cn.Istrategy;

/**
 * 
 * 策略A： 打八折
 */
public class StrategyA implements Strategy {

	public double cost(Double num) {
		return num * 0.8;
	}

}
