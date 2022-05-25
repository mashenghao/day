package cn.RchainOfResponsibility.news;

/**
 * 车头处理
 *
 * @author  mahao
 * @date:  2018年11月10日 下午12:02:42
 */
public class HeadCarHandler extends CarHandler{

	@Override
	public Car handler(Car car) {
		//1.自己的职责功能处理
		car.setHead("处理车头");
		//2.调用下一个职责链的处理
		if(this.nextHandler!=null){
			//3.调用下一个处理
			this.nextHandler.handler(car);
		}
		return car;
	}

}
