package basic.day17_conllections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import basic.day14_list.setDemo.User;

/**
 * 
 * 类名：集合的工具类collections的使用
 * 
 * @author mahao
 * @date 2018年6月27日 Description:
 */
public class Demo1 {

	public static void main(String[] args) {
		List<User> list = new ArrayList<User>();
		list.add(new User("user1"));
		list.add(new User("user2"));
		list.add(new User("user3"));
		for (User u : list) {
			System.out.println(u.getName());
		}
		// Collections.sort(list, c);
		List<String> list1 = new ArrayList<String>();
		
		list1.add("use2");
		list1.add("user3");
		list1.add("us1");
		list1.add("usr1");
		Collections.sort(list1,new StrCom());
		System.out.println(list1);
	}

}

/**
 * 
 * 类名：字符长度
 * 
 * @author mahao
 * @date 2018年6月27日 Description:
 */
class StrCom implements Comparator<String> {

	public int compare(String o1, String o2) {
		System.out.println(o1+"------"+o2);
		if (o1.length() > o2.length())
			return 1;
		if (o1.length() < o2.length())
			return -1;
		return 0;
	}

}