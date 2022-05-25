package cn.JobServer.my;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义被观察者基类
 *
 */
public class Observable {

	// 定义存储观察者对象的容器
	private List<Observer> list;
	private boolean change = false;
	
	public Observable() {
		
		list = new ArrayList<Observer>();
	}

	public Observable addObserver(Observer o ) {
		if(o==null){
			throw new NullPointerException();
		}
		if(!list.contains(o)){
			list.add(o);
		}
		return this;
	}
	
	public void hasChange(){
		change=true;
	}
	
	public void notifyObservers(Observable observable){
		if(!change){
			return;
		}
		for (Observer observer : list) {
			observer.execute(observable);
		}
	}
}
