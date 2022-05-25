package cn.Qmediator;

public abstract class Person {
	
	private String name;
	private int flag;
	
	public Person(String name, int flag) {
		this.name = name;
		this.flag = flag;
	}

	public abstract void checkFlag(Person p );

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
}
