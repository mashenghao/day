package basic.day14_list.genercIterator;

import java.util.ArrayList;

import basic.day14_list.iteratorPack.MyIterator;

/**
 * 定义一个集合，不带迭代器功能，如果要是 具备迭代器功能，则需要传入迭代器；
 *
 * @author mahao
 * @date: 2019年3月4日 下午4:05:05
 */
public class MyList<T> extends ArrayList<T> {
	
	public void getIterator(MyIterator<? super T> e) {
		while(e.hasNext()) {
			System.out.println(e.next());
		}
	}

}
