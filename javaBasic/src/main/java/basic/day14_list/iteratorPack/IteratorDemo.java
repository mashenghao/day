package basic.day14_list.iteratorPack;

import java.util.ListIterator;

/**
 * 集合容器中迭代器分析
 *
 * @author  mahao
 * @date:  2019年3月2日 下午3:15:31
 */
public class IteratorDemo {
	
	public static void main(String[] args) {
		MyList<String> list = new MyList<String>();
		list.add("001");	
		list.add("002");	
		list.add("003");	
		list.add("004");
		
		//迭代器遍历最好采用for循环，iterator对象，在for循环结束后就会被回收
		//，while则是方法结束后才会变量失效。
		for(MyIterator<String> it = list.getIterator();it.hasNext();) {
			String str = it.next();
			if(str.equals("003")) {
				it.remove();
			}
			System.out.println(str);
		}
		System.out.println(list);
			
	}
	
	
	public void method_listIterator() {
		MyList<String> list = new MyList<String>();
		list.add("001");	
		list.add("002");	
		list.add("003");	
		list.add("004");
		ListIterator<String> iterator = list.listIterator();
		while(iterator.hasNext()) {
			
		}
	}
}
