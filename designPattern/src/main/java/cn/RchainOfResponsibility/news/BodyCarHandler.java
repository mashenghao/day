package cn.RchainOfResponsibility.news;

public class BodyCarHandler extends CarHandler {

	@Override
	public Car handler(Car car) {
		car.setBody("处理车身");
		// 2.调用下一个职责链的处理
		if (this.nextHandler != null) {
			// 3.调用下一个处理
			this.nextHandler.handler(car);
		}
		return car;
	}

}
