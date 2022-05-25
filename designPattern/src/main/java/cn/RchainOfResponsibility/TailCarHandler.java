package cn.RchainOfResponsibility;

public class TailCarHandler extends CarHandler {

	@Override
	public void handler() {
		System.out.println("车尾处理------");
		// 2.调用下一个职责链的处理
		if (this.nextHandler != null) {
			// 3.调用下一个处理
			this.nextHandler.handler();
		}
	}

}
