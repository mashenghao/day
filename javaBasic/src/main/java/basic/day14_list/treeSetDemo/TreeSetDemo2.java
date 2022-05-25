package basic.day14_list.treeSetDemo;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * TreeSet也是无顺序的，也有自己的排序规则
 *
 * TreeSet按照比较器方式比较
 *
 * @author mahao
 * @date: 2019年3月3日 上午11:22:30
 */
public class TreeSetDemo2 {

	public static void main(String[] args) {
		
		Set<User> set = new TreeSet<User>(new MyComparator());
		set.add(new User("11", 5));
		set.add(new User("12", 3));
		set.add(new User("13", 7));
		set.add(new User("14", 1));
		set.add(new User("16", 1));
		for(User u : set) {
			System.out.println(u.getName()+"---"+u.getAge());
		}
		
	}
	
}

class MyComparator implements Comparator<User>{
	@Override
	public int compare(User o1, User o2) {
		int num = o2.getAge() - o1.getAge();
		if(num==0)
			return o2.getName().compareTo(o1.getName());
		return num;
	}
	
}