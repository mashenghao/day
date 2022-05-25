package cn.Qmediator.news;

public abstract class Person {

	/*
	 * 父类中含有中介者的引用
	 */
	private Mediator mediator;
	private String name;
	private int flag;

	public Person(String name, int flag, Mediator mediator) {
		this.name = name;
		this.flag = flag;
		this.mediator = mediator;
	}

	public abstract void checkFlag(Person p);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}
}
