package cn.Siterator.ex2;

import cn.Siterator.Book;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BookList {
	
	/**数据源*/
	private List<Book> list;
	public BookList(List<Book> list) {
		this.list = list;
	}
	
	/** 返回的是迭代器实例对象*/
	public Iterator<Book> iterator() {
		return new Ite();
	}
	
	/** 内部类，实现iterator方法，进行迭代*/
	private class Ite implements Iterator<Book> {

		int cursor = 0;// 游标

		public boolean hasNext() {
			return cursor != list.size();
		}

		public Book next() {
			if (cursor >= list.size())
				throw new NoSuchElementException();
			return list.get(cursor++);
		}

	}
}
