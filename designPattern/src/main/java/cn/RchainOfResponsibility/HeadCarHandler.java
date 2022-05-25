package cn.RchainOfResponsibility;

/**
 * 车头处理
 *
 * @author  mahao
 * @date:  2018年11月10日 下午12:02:42
 */
public class HeadCarHandler extends CarHandler{

	@Override
	public void handler() {
		//1.自己的职责功能处理
		System.out.println("车头处理-------------");
		//2.调用下一个职责链的处理
		if(this.nextHandler!=null){
			//3.调用下一个处理
			this.nextHandler.handler();
		}
	}

}
