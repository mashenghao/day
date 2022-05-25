package cn.Qmediator.news;

public class Woman extends Person {

	public Woman(String name, int flag, Mediator mediator) {
		super(name, flag, mediator);
	}

	@Override
	public void checkFlag(Person p) {
		this.getMediator().setWoman(this);
		this.getMediator().checkFlag(p);
	}

}
