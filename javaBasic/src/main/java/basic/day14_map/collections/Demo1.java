package basic.day14_map.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

/**
 * 集合工具类的使用
 *
 * @author mahao
 * @date: 2019年3月5日 下午9:54:49
 */
public class Demo1 {

	// 对list元素排序
	@Test
	public void demo1() {
		// list元素进行排序，则元素必须具有比较顺序，才可以;
		List<User> list = new ArrayList<User>();
		list.add(new User("001", 15));
		list.add(new User("003", 1));
		list.add(new User("005", 5));
		list.add(new User("009", 19));
		list.add(new User("001", 19));
		// 接受的参数是List<T extends Comparable <? super T>> list
		Collections.sort(list);
		System.out.println(list);
		// 2.传入比较器,相反排序；比较器Comparator<> c 
		Collections.sort(list,new MyComparator());
		System.out.println(list);
	}
	
	public <T> void binarySearch(List<? extends Comparable<? super T>> list) {
		
	}
	
	@Test
	public void demo2() {
		List<String> synchronizedList = Collections.synchronizedList(new ArrayList<String>());
	}
}





class MyComparator implements Comparator<User> {
	
	public int compare(User o2, User o1) {
		int num = o1.getName().compareTo(o2.getName());
		return num == 0 ? o1.getAge() - o2.getAge() : num;
	}

}

class User extends Person implements Comparable<User> {

	private String name;
	private int age;

	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public int compareTo(User o) {
		int num = name.compareTo(o.getName());
		return num == 0 ? age - o.getAge() : num;
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

	@Override
	public String toString() {
		return "[name=" + name + ", age=" + age + "]";
	}

}

class Person {

}