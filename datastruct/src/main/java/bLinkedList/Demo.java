package bLinkedList;

import org.junit.Test;

//练习
public class Demo {

	// 循环链表，猴子选大王
	@Test
	public void demo1() {

		int num = 10;
		// 构造循环链表
		Node first = new Node(null, 1, null);
		Node last = first;
		for (int i = 2; i <= 10; i++) {
			Node n = new Node(last, i, null);
			last.next = n;
			last = n;
		}
		first.prev = last;
		last.next = first;

		// 选大王
		Node p = first;
		int i = 1;
		while (p.next != p) {// 当只剩下一个节点时，即next指向自己
			if (i == 3) {
				System.out.println("删除元素为--" + p.item);
				// 删除当前元素节点
				p.prev.next = p.next;
				p.next.prev = p.prev;
				p = p.next;
				i = 1;
			} else {
				p = p.next;
				i++;
			}
		}
		System.out.println(p.item);
	}
}
