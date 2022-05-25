package cn.Istrategy;

/**
 * 
 * 策略B： 打九折
 */
public class StrategyB implements Strategy {

	public double cost(Double num) {
		return num * 0.9;
	}

}
