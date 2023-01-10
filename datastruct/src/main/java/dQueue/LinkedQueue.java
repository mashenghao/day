package dQueue;

import java.util.Iterator;

/**
 * 带头节点的队列<br>
 * 第一个元素，不做存储，作为便于操作使用
 *
 * @author mahao
 * @date: 2019年3月21日 下午9:20:57
 * @param <E>
 */
public class LinkedQueue<E> implements Queue<E> {

	Node<E> front;// 一直指向空的头指针
	Node<E> rear;

	public LinkedQueue() {
		Node<E> voidHead = new Node<E>(null, null);
		front = rear = voidHead;
	}

	// 当头指针，和尾指针指向同一个voidHead表示空
	@Override
	public boolean isEmpty() {
		return front == rear;
	}

	@Override
	public boolean isFull() {
		return true;
	}

	@Override
	public void inQueue(E e) {
		Node<E> node = new Node(e, null);
		// 进队尾,队尾指针后移
		rear.next = node;
		rear = node;
	}

	@Override
	public E outQueue() {
		// 判断空
		if (front == rear) {
			System.out.println("队列空----");
			return null;
		}
		Node<E> p = front.next;
		front.next = p.next;// 头指针向后移，摒弃队头元素
		if (p == rear) {// 表示取得是队列的第一个最后一个元素
			rear = front;// 让队尾=队头
		}
		return p.data;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
