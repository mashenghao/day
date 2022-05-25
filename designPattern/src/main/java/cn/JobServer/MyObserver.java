package cn.JobServer;

import java.util.Observable;
import java.util.Observer;

/**
 *	观察者 
 */
public class MyObserver implements Observer{
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("调用这个方法");
	}
	
}
