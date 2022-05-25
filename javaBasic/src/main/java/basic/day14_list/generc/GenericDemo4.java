package basic.day14_list.generc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 泛型限定 ？的作用1
 *
 * @author mahao
 * @date: 2019年3月3日 下午8:38:01
 */
public class GenericDemo4 {

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<String>();
		list1.add("aaa");
		list1.add("bbb");
		list1.add("ccc");
		
		List<Parent> list2 = new ArrayList<Parent>();
		list2.add(new Parent());
		list2.add(new Son());
		list2.add(new Son());
		
		//使用占位符的打印
		print(list1);
		print2(list2);
		
	}
	
	/**
	 * 声明一个打印集合的方法
	 * 用占位符代替
	 */
	public static void print(List<?> list){
		Iterator<?> iterator = list.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	/**
	 * 声明一个打印集合的方法2
	 * 这个方法定义为泛型方法,定义的泛型可以直接使用，表示传入的具体
	 * 类型
	 */
	public static <T> void print2(List<T> list){
		Iterator<T> iterator = list.iterator();
		while(iterator.hasNext()) {
			T t = iterator.next();
			System.out.println(t);
		}
	}
}
