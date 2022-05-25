package cn.RchainOfResponsibility.news;

public abstract class CarHandler {

	protected CarHandler nextHandler;
	
	/**
	 * 设置下一个职责的handler
	 * 
	 * @param carHandler
	 * @return
	 */
	public CarHandler setNextHandler(CarHandler carHandler) {
		this.nextHandler = carHandler;
		return this.nextHandler;
	}

	public abstract Car handler(Car car);
}
