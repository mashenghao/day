package cn.RchainOfResponsibility;

/**
 * 职责链模式
 * 
 * 每部分负责各自的功能，其余部分功能交由 下一个handler去处理，
 * 每个handler含有 下一个handler的引用
 *
 * @author mahao
 * @date: 2018年11月10日 上午11:56:45
 */
public class MainClass {

	public static void main(String[] args) {
		// 1.实例化责任链上handler
		CarHandler head = new HeadCarHandler();
		CarHandler body = new BodyCarHandler();
		CarHandler tail = new TailCarHandler();
		// 2.设定执行关系
		head.setNextHandler(body).
				setNextHandler(tail);
		// 3.调用执行
		head.handler();
	}
}
