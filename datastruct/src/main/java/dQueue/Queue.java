package dQueue;

import java.util.Iterator;

public interface Queue<E> extends Iterable<E> {

	public boolean isEmpty();

	public boolean isFull();

	public void inQueue(E e);

	public E outQueue();

	@Override
	public Iterator<E> iterator();

}
