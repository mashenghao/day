package cn.JobServer;

import java.util.Observable;

/**
 * 被观察者
 * 
 * @author mahao
 *
 */
public class MySubject extends Observable{
	
	private String name;

	public String getName() {
		return name;
	}
	
	/**
	 * 监听这个方法，方法改变，唤醒监听者
	 * 
	 * @param name
	 * @author mahao
	 */
	public void setName(String name) {
		this.name = name;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public String toString() {
		return "MySubject [name=" + name + "]";
	}
	
	
}
