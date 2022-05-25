package cn.RchainOfResponsibility;

/**
 * 车身处理
 *
 * @author  mahao
 * @date:  2018年11月10日 下午12:34:18
 */
public class BodyCarHandler extends CarHandler {

	@Override
	public void handler() {
		System.out.println("车身处理-------");
		// 2.调用下一个职责链的处理
		if (this.nextHandler != null) {
			// 3.调用下一个处理
			this.nextHandler.handler();
		}
	}

}
