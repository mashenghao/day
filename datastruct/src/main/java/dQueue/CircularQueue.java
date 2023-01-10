package dQueue;

import java.util.Iterator;

/**
 * 循环队列：<br>
 * 少用一个元素，用来区分队列是空还是满了
 * 
 * 如果（rear+1）%size = front 为队满 <br>
 * 如果 front=rear; 为队空；
 *
 * @author mahao
 * @date: 2019年3月21日 下午8:47:20
 * @param <E>
 */
public class CircularQueue<E> implements Queue<E> {

	Object objs[]; // 队列头出，尾进
	int front; // 头
	int rear; // 尾
	int size = 5;

	public CircularQueue() {
		objs = new Object[10];
		front = rear = 0;
	}

	@Override
	public boolean isEmpty() {
		return rear == front;
	}

	@Override
	public boolean isFull() {
		return (rear + 1) % size == front;
	}

	@Override
	public void inQueue(E e) {
		// 判读队满---循环队列出去空的标识列，是否还有其余空的
		if ((rear + 1) % size == front) {
			System.out.println("队满-----");
			return ;
		}
		objs[rear] = e;
		rear = (rear + 1) % size;
	}

	@Override
	public E outQueue() {
		if (rear == front) {
			System.out.println("队空");
			return null;
		}
		E e = (E) objs[front];
		front = (front + 1) % size;
		return e;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

}
