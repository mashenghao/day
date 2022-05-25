package cn.Siterator.ex1;

import cn.Siterator.Book;

import java.util.List;

/**
 * 容器，用于存储书，以验证迭代模式
 * 
 * 迭代方式一： 自身实现迭代
 * 
 * 自身实现迭代，暴露接口，实现数据的迭代显示
 * 
 * @author mahao
 * @date: 2018年11月10日 下午5:29:52
 */
public class BookList1 {

	private List<Book> list;
	private int index = 0;

	public BookList1(List<Book> list) {
		this.list = list;
	}

	public boolean hasNext() {
		return index < list.size();
	}

	public Book next() {
		return list.get(index++);
	}
	
	/**
	 * 方式二：直接返回数据形式，让调用者自己迭代
	 */
	public List<Book> getBooks(){
		return list;
	}
}
