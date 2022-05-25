package basic.day14_list.generc;

import basic.day14_list.treeSetDemo.User;

/**
 * 泛型测试
 *
 * @author  mahao
 * @date:  2019年3月3日 下午8:38:01
 */
public class GenericDemo<T> {
	
	//1.属性T的类型，则和对象创建时传入的T保持统一。
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
		
	}
	
	/**
	 * 2. 泛型定义在方法上，方法可以接受不同类型的数据,记住格式；
	 * @param t
	 */
	public <T> T show(T t) {
		System.out.println(t);
		return t;
	}
	
	/**
	 * 静态方法上，不能使用类上定义的泛型，类上的泛型是
	 * 对象在创建时才确定的，静态方法先于对象存在；
	 * @param w
	 * @return
	 */
	public static <W> W staticMethod(W w) {
		return w;
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException {
		//1. 泛型声明在类上，则对象一生成，整个对象操作的则是这个类型
		GenericDemo<User> demo = new GenericDemo<User>();
		demo.setT(new User("mahao",15));
		User user = demo.getT();
		System.out.println(user.toString());
		
		//2.泛型定义在方法上，则这个方法具有接受不同类型的数据
		demo.show(user);
		demo.show("mahao");
		
		//3.调用定义在静态方法上定义的泛型
		User staticMethod = GenericDemo.staticMethod(user);
		Integer staticMethod2 = GenericDemo.staticMethod(15);
	}
	
}
