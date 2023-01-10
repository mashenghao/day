package dQueue;

import java.util.Iterator;

public class BasicQueue<E> implements Queue<E>{

	Object objs[]; // 队列头出，尾进
	int front; // 头
	int rear; // 尾

	public BasicQueue() {
		objs = new Object[10];
		front = rear = 0;
	}

	public boolean isEmpty() {
		return front == rear;
	}

	public boolean isFull() {
		return rear == 10;
	}

	public void inQueue(E e) {
		if (isFull())
			System.out.println("队列满----");
		objs[rear++] = e;
	}

	public E outQueue() {
		if (isEmpty())
			System.out.println("队列空");
		return (E) objs[front++];
	}

	@Override
	public Iterator<E> iterator() {
		return new Iter();
	}
	
	class  Iter implements Iterator<E>{

		@Override
		public boolean hasNext() {
			return ! isEmpty();
		}

		@Override
		public E next() {
			return outQueue();
		}
		
	}
}
