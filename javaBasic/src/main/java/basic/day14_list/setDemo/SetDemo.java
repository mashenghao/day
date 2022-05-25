package basic.day14_list.setDemo;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
/**
 * 
 * 类名：set的判断元素重复是根据hash值，要判断，需要自定义
 * 	生成的hash值
 * 
 * hashSet 保证元素对象的唯一性：
 * 		1-通过复写hashSet方法，改变要存储对象的hashcode值，
 * 			先判断hash值是否一样
 * 		2-hash值一样时，通过equal方法判断；
 * 
 * @author mahao
 * @date 2018年6月25日
 * Description:
 */
public class SetDemo {
	
	@Test
	public void demo2() {
		Set<User> set =new HashSet<User>();
		
		User u1 = new User("user1");
		set.add(u1);
		
		User u2 = new User("user2");
		set.add(u2);
		
		User u3 = new User("user3");
		set.add(u3);
		
		User u4 = new User("user2");
		set.add(u2);
		
		for(User u: set){
			System.out.println(u.getName());
		}
		
		for(Iterator<User> it= set.iterator();it.hasNext();) {
			System.out.println(it.next().getName());
		}
	}
}
