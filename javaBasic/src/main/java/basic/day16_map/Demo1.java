package basic.day16_map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * map 遍历方式
 * 类名：存入user，根据用户的用户名和密码作为比较对象作为key的值
 *    对于hash的数据结构，排序方式是hascode（）方法，和equals（）方法的覆盖
 * 
 * @author mahao
 * @date 2018年6月27日
 * Description:
 */
public class Demo1 {
	
	public static void main(String[] args) {
		User u1 = new User("u1","p1");
		User u2 = new User("u2","p1");
		User u3 = new User("u3","p3");
		User u4 = new User("u4","p4");
		Map<User,String> map = new HashMap<User,String>();
		map.put(u1, "1");
		System.out.println("-------------------");
		map.put(u2, "2");
		System.out.println("-------------------");
		map.put(u3, "3");
		System.out.println("-------------------");
		map.put(u4, "4");
		System.out.println("-------------------");
		Set<User> set= map.keySet();
		for(User u : set){
			System.out.println(map.get(u));
		}
	}
	
}
