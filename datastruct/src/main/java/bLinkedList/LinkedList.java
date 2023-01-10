package bLinkedList;

import java.util.Iterator;

public class LinkedList<E> implements Iterable<E> {

	Node<E> head; // 头指针
	Node<E> first; // 首个元素
	Node<E> last;// 尾指针

	public LinkedList() {
		head = new Node<E>(null, null, null);
		last = head;
	}

	public void add(E e) {
		Node<E> node = new Node<E>(last, e, null);
		last.next = node;
		last = node;
	}

	public boolean set(int index, E e) {
		Node p = head.next;
		int i = 0;
		while (p != null && i < index) {
			p = p.next;
			i++;
		}
		if (p == null || i < index) {
			return false;
		}
		Node node = new Node(p.prev, e, p.next);
		p.prev.next = node;
		return true;
	}

	public boolean isEmpty() {
		return head.next == null;
	}

	public E get(int index) {
		Node<E> p = head.next;
		int i = 0;
		while (p != null && i < index) {
			i++;
			p = p.next;
		}
		if (p == null || i < index)
			return null;
		return p.item;
	}

	public void buildAZ() {
		clean();
		for (int i = 'A'; i < 'Z'; i++) {
			Node<Integer> node = new Node<Integer>((Node<Integer>) last, i, null);
			last.next = (Node<E>) node;
			last = (Node<E>) node;
		}
	}

	public void clean() {
		head.next = null;
		last = head;
	}

	public void initTail(E[] es) {
		for (int i = 0; i < es.length; i++) {// 尾插法
			Node<E> node = new Node<E>(last, es[i], null);
			last.next = node;
			last = node;
		}
	}

	public void initHead(E[] es) {
		for (int i = 0; i < es.length; i++) {// 头插法
			Node<E> node = new Node<E>(head, es[i], head.next);
			head.next = node;
		}
	}

	@Override
	public String toString() {
		Node<E> p = head.next;
		while (p != null) {
			System.out.print(p.item + " ");
			p = p.next;
		}
		System.out.println();
		return super.toString();
	}

	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	@Override
	public Iterator<E> iterator() {

		return new Iter();
	}

	private class Iter implements Iterator<E> {

		Node<E> head = LinkedList.this.head;
		Node<E> last = head.next;

		@Override
		public boolean hasNext() {
			if (last != null)
				return true;
			return false;
		}

		@Override
		public E next() {
			E e = last.item;
			last = last.next;
			return e;
		}
	}

}
