package cn.RchainOfResponsibility;
/**
 * 抽象父类
 *
 * @author  mahao
 * @date:  2018年11月10日 下午12:31:56
 */
public abstract class CarHandler {

	protected CarHandler nextHandler;
	
	/** 设置下一个职责的handler*/
	public CarHandler setNextHandler(CarHandler carHandler) {
		this.nextHandler = carHandler;
		return this.nextHandler;
	}
	/**事件处理通用接口*/
	public abstract void handler();
}
