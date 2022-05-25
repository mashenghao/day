package basic.day14_list.treeSetDemo;

import java.util.Set;
import java.util.TreeSet;

/**
 * TreeSet也是无顺序的，也有自己的排序规则
 *
 * TreeSet按照元素的自然顺序排序，元素必须实现Comparable接口；
 *
 * @author mahao
 * @date: 2019年3月3日 上午11:22:30
 */
public class TreeSetDemo {

	public static void main(String[] args) {

		Set<User> set = new TreeSet<User>();
		set.add(new User("11", 5));
		set.add(new User("12", 3));
		set.add(new User("11", 7));
		set.add(new User("14", 1));
		set.add(new User("16", 1));
		for(User u : set) {
			System.out.println(u.getName()+"---"+u.getAge());
		}
	}
	
	
	
}
