package basic.day14_list.generc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

/**
 * 泛型限定 ？的作用2
 *
 * @author mahao
 * @date: 2019年3月3日 下午18:38:01
 */
public class GenericDemo3 {

	
	List<Parent> list1 = new ArrayList<Parent>();
	List<Son> list2 = new ArrayList<Son>();
	
	@Before
	public void init() {
		list1.add(new Parent());
		list1.add(new Son());
		
		list2.add(new Son());
	}
	
	/**
	 * ? 叫通配符或者占位符，这个是在泛型类型不确定的时候使用的，
	 * 使用这个有上限和下限的区分
	 * 1. ? extends Parent 上限
	 * 2. ? super Son 下限
	 * 
	 */
	@Test
	public void demo5() {
		//1. 上限案例
		/*
		 * 集合容器，集合可以添加list.addAll(Collection<? extends E>),
		 * 集合可以添加其他容器数据，只要该容器元素是集合的同一类 或者子类
		 * list1泛型是Parent list2泛型是Son
		 */
		list1.addAll(list2);
		
		//2.下限案例，下限案例一般是要求子类本身，或者子类父类具备什么能力，
		//如果父类具有，子类则可以继承
		/*
		 * 声明一个函数内部类，比较器对象，比较传入的泛型是Parent，则Parent具备比较功能
		 * 则子类继承它，也具备了比较功能，所以TreeSet的构造方法传入的比较器，
		 * 是Son要比较，比较器传入的是父类 Comparator<? super E> E表示Son
		 * 
		 */
		class MyComparator implements Comparator<Parent>{//Comparator<? super E>
			public int compare(Parent o1, Parent o2) {
				return 1;
			}
		}
		
		Set<Son> set = new TreeSet<Son>(new MyComparator());//Comparator<? super E>
		set.add(new Son());
		set.add(new Son());
		System.out.println(set);
	}
	
	
	
	@Test
	public void demo1() {
		
		//如果泛型定义和和方法定义的类型是一样的，则可以直接打印
		print(list1);
		
		//List<Parent> list = new ArrayList<Son>();  //错误的定义形式
		//如果泛型定义和和方法定义的类型不是一样的，则不可以使用，包括子类也无法使用；
		//print(list2);
				
	}
	
	/**
	 * 实现可以方法传入的参数是Parent类型，或者Son类型
	 * 可以使用占位符，将泛型类型用？ 空着，这传入的类型，就不加限制
	 * 类似于Object
	 */
	@Test
	public void demo2() {
		
		//2.如何使可以用子类也加入呢，可以使用这种方式，用占位符？ ，但是就
		//要打印的要求是Parent或者子类，如何限定
		print2(list1);
		print2(list2);
				
	}
	
	/**
	 * 显示符用法2 显示泛型范围
	 */
	@Test
	public void demo3() {
		System.out.println("通过泛型限定的方式，限制操作的是Parent的子类还有自己");
		print3(list1);
		print3(list2);
	}
	
	
	@Test
	public void demo4() {
		
	}
	
	/**
	 * 1.声明一个打印的方法，来打印特定的集合类型
	 */
	public static void print(List<Parent> list) {
		Iterator<Parent> it = list.iterator();
		while(it.hasNext()) {
			Parent p = it.next();
			System.out.println(p.toString());
		}
	}
	
	/**
	 * 声明一个打印集合的方法
	 * 用占位符代替
	 */
	public static void print2(List<?> list){
		Iterator<?> iterator = list.iterator();
		while(iterator.hasNext()) {
			//但是无法通过？ 知道泛型的类型，也无法操作里面的方法
			System.out.println(iterator.next());
		}
	}
	
	/**
	 * 通过类型限定，让泛型接收的只能是Parent类以及子类,
	 * 则方法里面可以操作泛型的基类
	 * @param list
	 */
	public static void print3(List<? extends Parent> list) {
		Iterator<? extends Parent> it = list.iterator();
		while(it.hasNext()) {
			Parent p = it.next();
			System.out.println(p.toString());
		}
	}
	
}
