package cn.Siterator.ex2;

import cn.Siterator.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 使用迭代器模式
 * 
 * 与案例一不同的是，使用迭代器模式的好处是，
 * 用于迭代操作的是一个新的类，将迭代功能从
 * 类中分离出来。利用类功能分割。
 *
 * @author  mahao
 * @date:  2018年11月10日 下午5:47:14
 */
public class MainClass {
	public static void main(String[] args) {
		List<Book> list = new ArrayList<>();
		Book b1 = new Book();
		b1.setName("java");
		b1.setPrice(1000);
		Book b2 = new Book();
		b2.setName("android");
		b2.setPrice(5f);
		list.add(b1);
		list.add(b2);
		//准备数据
		BookList list2 = new BookList(list);
		Iterator<Book> iterator = list2.iterator();
		while(iterator.hasNext()){
			Book b = iterator.next();
			System.out.println(b.toString());
		}
	}
}
