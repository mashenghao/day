package basic.day14_list.iteratorPack;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * 自定义集合容器
 *
 * @author mahao
 * @param <E>
 * @date: 2019年3月2日 下午3:28:22
 */
public class MyList<E> extends ArrayList<E> implements MyIterable<E> {

	/**
	 * 实现获取迭代器的接口
	 */
	@Override
	public MyIterator<E> getIterator() {
		return new Iter2();
	}

	private class Iter2 implements MyIterator<E> {

		int myCourse;//下表
		int mySize = size();
		int lastRet = -1;
		
		@Override
		public boolean hasNext() {
			
			return myCourse != mySize;
		}

		@Override
		public E next() {
			if(mySize!=size())
				throw new ConcurrentModificationException("迭代器遍历过程中发生更改");
			if(myCourse < mySize) {
				lastRet=myCourse;
				return get(myCourse++);
			}else {
				throw new IndexOutOfBoundsException("没有这个元素异常，数组越界");
			}
			
		}

		public E remove() {
			if(lastRet<0)
				throw new IllegalStateException("无最近遍历操作"); 
			E e = MyList.this.remove(lastRet);
			if(myCourse > lastRet)
				myCourse--;
			lastRet = -1;
			mySize = size();
			return e;
		}

	}

}
