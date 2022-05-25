package basic.source.mh;


import java.io.Serializable;
import java.util.*;


/**
 * LinkedList双向链表的实现
 *
 * @param <E>
 */
public class LinkedList<E> extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Serializable, Cloneable {

    transient int size;

    transient Node<E> first;

    transient Node<E> last;


    /**
     * AbstractSequentialList
     **/
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    //插入头部
    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> node = new Node<>(null, e, f);
        first = node;
        if (f == null)
            last = node;
        else
            f.prev = node;
        modCount++;
        size++;
    }

    //插入尾部
    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> node = new Node<>(l, e, null);
        last = node;
        if (l == null)
            first = node;
        else
            l.prev = node;
        modCount++;
        size++;
    }

    private void linkBeforeNode(E e, Node<E> node) {
        Node<E> pred = node.prev;
        Node<E> next = node.next;
        //插入到指定元素之前
        Node<E> newNode = new Node<>(pred, e, node);
        next.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        modCount++;
        size++;
    }


    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    //插入到指定位置
    @Override
    public void add(int index, E element) {
        checkAddRange(index);
        if (index == size)
            linkLast(element);
        else
            linkBeforeNode(element, node(index));
    }


    private Node<E> node(int index) {
        if (index < (size >> 2)) {//顺序
            Node<E> p = first;
            for (int i = 0; i < index; i++)
                p = p.next;
            return p;
        } else {//逆序
            Node<E> p = last;
            for (int i = size - 1; i > index; i--)
                p = p.prev;
            return p;

        }
    }

    private void checkAddRange(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("下标越界——" + index);
    }

    private void checkGetRange(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("下标越界——" + index);
    }

    @Override
    public E get(int index) {
        checkGetRange(index);
        return node(index).e;
    }


    @Override
    public E set(int index, E element) {
        checkGetRange(index);
        Node<E> node = node(index);
        E oldVal = node.e;
        node.e = element;
        return oldVal;
    }


    @Override
    public E remove(int index) {
        checkGetRange(index);

        return unlink(node(index));
    }

    private E unlink(Node<E> node) {
        Node<E> pred = node.prev;
        Node<E> next = node.next;
        E ele = node.e;
        if (pred == null)
            first = next;
        else {
            pred.next = next;
            node.prev = null;
        }

        if (next == null)
            last = pred;
        else {
            last.prev = pred;
            node.next = null;
        }

        node.e = null;
        modCount++;
        size--;

        return ele;
    }

    private E unlinkFirst(Node<E> f) {
        Node<E> next = f.next;
        E e = f.e;
        f.e = null;
        f.next = null;
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        modCount++;
        return e;
    }

    private E unlinkLast(Node<E> f) {
        final Node<E> pred = f.prev;
        E e = f.e;
        f.next = null;
        f.e = null;
        if (pred == null) {
            first = null;
        } else {
            pred.next = null;
        }
        modCount++;
        size--;
        return e;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {

        //TODO 尚未实现
        return true;
    }

    /**
     * Deque
     **/
    @Override
    public void addFirst(E e) {
        linkFirst(e);
    }

    @Override
    public void addLast(E e) {
        linkLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }


    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E pollFirst() {
        return null;
    }

    @Override
    public E pollLast() {
        return null;
    }

    @Override
    public E getFirst() {
        return null;
    }

    @Override
    public E getLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    /**
     * 元素类型
     *
     * @param <E>
     */
    private static class Node<E> {
        E e;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E e, Node<E> next) {
            this.e = e;
            this.prev = prev;
            this.next = next;
        }
    }
}
