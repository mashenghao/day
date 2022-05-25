package cn.Fprototype;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现接口，表名是克隆
 */
public class Person implements Cloneable{
	
	private String name ;
	private int age;
	private List<String> friends;
	
	public Person clone(){
		try {
			/*
			 *1. 浅度克隆，对对象那个的引用无法在深度克隆
			 */
			//return (Person) super.clone();
			
			/*
			 * 2.深度克隆,对深层次的对象，手动封装数据
			 * 
			 */
			Person p = (Person) super.clone();
			List<String> friends = new ArrayList<String>();
			for (String f : this.getFriends()) {
				friends.add(f);
			}
			p.setFriends(friends);
			return p ;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
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
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", friends=" + friends + "]";
	}
	
}
