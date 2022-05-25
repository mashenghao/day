package cn.Siterator.ex1;

import cn.Siterator.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 遍历方式
 *
 * @author mahao
 * @date: 2018年11月10日 下午5:27:48
 */
public class MainClass {

	private List<Book> list;

	@Before
	public void before() {
		list = new ArrayList<>();
		Book b1 = new Book();
		b1.setName("java");
		b1.setPrice(1000);
		Book b2 = new Book();
		b2.setName("android");
		b2.setPrice(5f);
		list.add(b1);
		list.add(b2);

	}
	
	@Test
	public void demo1() {
		BookList1 list1 = new BookList1(list);
		while (list1.hasNext()) {
			Book b = list1.next();
			System.out.println(b);
		}
	}
	
	
}
