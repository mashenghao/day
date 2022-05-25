package cn.Umemento;

/**
 * 保存对象状态的类，相当于备忘录功能
 * 
 * @author maho
 *
 */
public class Memento {
	
	/*
	 * 里面含有要被备份的对象的基本属性
	 * 
	 */
	private String name;
	private int age;
	
	
	public Memento(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
