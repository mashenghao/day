package cn.Qmediator.news;

public class Man extends Person {

	public Man(String name, int flag, Mediator mediator) {
		super(name, flag, mediator);
	}

	/**
	 * 这里调用引用的中介者中的方法
	 */
	public void checkFlag(Person p) {
		this.getMediator().setMan(this);
		this.getMediator().checkFlag(p);
	}

}
