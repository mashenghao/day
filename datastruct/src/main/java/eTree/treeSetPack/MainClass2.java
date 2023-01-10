package eTree.treeSetPack;

import java.util.Iterator;

/**
 * 二叉树
 *
 * @author mahao
 * @date: 2019年3月22日 下午6:47:37
 */
public class MainClass2 {

	public static void main(String[] args) {

		TreeSet<User> set = new TreeSet<User>();
		set.add(new User("11", 5,"flag--11"));
		set.add(new User("16", 1,"flag--16"));
		set.add(new User("11", 7,"flag--11"));
		set.add(new User("14", 1,"flag---14"));
		set.add(new User("16", 1,"flag--18"));
		Iterator<User> it = set.iterator();
		while(it.hasNext()) {
			User u = it.next();
			System.out.println(u.getName()+"---"+u.getAge()+"-----"+u.getStr());
		}
	}
}
