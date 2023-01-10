package dQueue;

import java.util.Iterator;

import org.junit.Test;

/**
 * 队列
 *
 * @author mahao
 * @date: 2019年3月21日 下午8:16:58
 */
public class MainClass {

	@Test
	public void demo1() {
		Queue<String> q = new BasicQueue<String>();
		q.inQueue("1");
		q.inQueue("2");
		String string = q.outQueue();
		System.out.println(string);
		q.inQueue("3");
		q.inQueue("4");
		Iterator<String> it = q.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	@Test
	public void demo2() {
		Queue<String> q = new CircularQueue<String>();
		q.inQueue("1");
		q.inQueue("2");

		String s1 = q.outQueue(); 
		System.out.println("出队"+s1);
		String s2 = q.outQueue(); 
		System.out.println("出队"+s2);
		
		q.inQueue("3");
		q.inQueue("4");
		q.inQueue("5");
		q.inQueue("6");
		while (!q.isEmpty()) {
			String queue = q.outQueue();
			System.out.println(queue);
		}
	}
}
