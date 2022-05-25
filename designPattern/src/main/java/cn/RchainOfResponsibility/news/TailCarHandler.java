package cn.RchainOfResponsibility.news;

public class TailCarHandler extends CarHandler {

	@Override
	public Car handler(Car car) {
		car.setTail("处理车尾");
		// 2.调用下一个职责链的处理
		if (this.nextHandler != null) {
			// 3.调用下一个处理
			this.nextHandler.handler(car);
		}
		return car;
	}

}
