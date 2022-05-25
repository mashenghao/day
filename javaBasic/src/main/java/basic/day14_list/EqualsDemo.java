package basic.day14_list;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import basic.day14_list.setDemo.User;

/**
 * 
 * 类名： contains 和 底层equals 方法
 * 
 *  将名字不同的用户存入list中
 * 
 * @author mahao
 * @date 2018年6月25日
 * Description:
 */
public class EqualsDemo {
	
	@Test
	public void demo1(){
		List<User> list = new ArrayList<User>(); 
		list.add(new User("user1"));
		list.add(new User("user2"));
		list.add(new User("user3"));
		User user = new User("user4");
		
		if(!list.contains(user)){
			list.add(user);
		}
		
		for(User u : list){
			
			System.out.println(u.getName());
		}
	}
}
