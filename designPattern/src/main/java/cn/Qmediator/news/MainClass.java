package cn.Qmediator.news;

/**
 * 中介者模式 在Mediator模式中，类之间的交互行为被统一放在 Mediator的
 * 对象中，对象通过Mediator对象同其他对象交互，Mediator对象起着控制器 的作用
 *
 * @author mahao
 * @date: 2018年11月10日 下午4:25:23
 */
public class MainClass {

	public static void main(String[] args) {
		// 1.中介者
		Mediator mediator = new Mediator();
		// 2.实例绑定中介者
		Person zs = new Man("张三", 1, mediator);
		Person ls = new Man("李四", 2, mediator);
		Person xh = new Woman("小红", 2, mediator);
		//3.实例调用中介者的方法，进行数据比较
		ls.checkFlag(xh);
	}
}
