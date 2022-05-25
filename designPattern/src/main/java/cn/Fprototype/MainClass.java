package cn.Fprototype;

import java.util.ArrayList;
import java.util.List;

/**
 * 原型模式
 * 
 * 实现对一个实例对象的结构和数据的复制
 * 在堆内存中， 对要被克隆的对象的进行复制，复制成新的一个空间
 * 将这个空间的引用，给克隆的对象
 *
 */
public class MainClass {
	public static void main(String[] args) {
		Person p1 = new Person();
		p1.setAge(18);
		p1.setName("mahao");
		List friends = new ArrayList<String>();
		friends.add("1");
		friends.add("2");
		p1.setFriends(friends);
		Person p2 = new Person();
		p2= p1;
		Person p3 = new Person();
		p3=p1.clone();
		System.out.println("原对象"+p1+"引用"+p2+"克隆"+p3);
		//原对象Person [name=mahao, age=18, friends=[1, 2]]
		//引用Person [name=mahao, age=18, friends=[1, 2]]
		//克隆Person [name=mahao, age=18, friends=[1, 2]]
		p1.setName("lishuio");
		System.out.println("原对象"+p1+"引用"+p2+"克隆"+p3);
		//原对象Person [name=lishuio, age=18, friends=[1, 2]]
		//引用Person [name=lishuio, age=18, friends=[1, 2]]
		//克隆Person [name=mahao, age=18, friends=[1, 2]]
	}
}
