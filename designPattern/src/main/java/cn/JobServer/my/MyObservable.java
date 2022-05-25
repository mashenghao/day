package cn.JobServer.my;

/**
 * 被观察者
 * @author maho
 *
 */
public class MyObservable extends Observable{
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.hasChange();
		this.notifyObservers(this);
	}

	@Override
	public String toString() {
		return "MyObservable [name=" + name + "]";
	}
	
	
}
