package basic.day15_fanxing;

import basic.day14_list.setDemo.User;

public class Demo1<T> {
	
	private T t;
	
	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}



	public static void main(String[] args) {
		Demo1<User> d = new Demo1<User>();
		d.setT(new User(null));
		System.out.println(d.getT());
	}
	
	
}
