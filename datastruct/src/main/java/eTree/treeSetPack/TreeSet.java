package eTree.treeSetPack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eTree.Node;

/**
 * 自定义TreeSet
 *
 * @author mahao
 * @date: 2019年3月22日 下午9:31:28
 * @param <E>
 *            实现Comparable接口的类
 */
public class TreeSet<E extends Comparable<E>> implements Iterable<E> {

	Node<E> root;

	public E add(E e) {
		Node<E> node = new Node(e, null);
		if (root == null) {
			root = node;
			return null;
		} else {
			return work(root, e);
		}
	}

	private E work(Node<E> node, E e) {
		E n = (E) node.data;
		int flag = e.compareTo(n);
		if (flag > 0) {
			if (node.right == null) {
				node.right = new Node<>(e, null);
				return null;
			}
			return work(node.right, e);
		} else if (flag < 0) {
			if (node.left == null) {
				node.left = new Node<>(e, null);
				return null;
			}
			return work(node.left, e);
		} else {
			// System.out.println("数据相同，舍弃---数据相同");
			E oldE = (E) node.data;
			node.data = e;
			return oldE;
		}

	}

	@Override
	public Iterator<E> iterator() {
		return new Iter();
	}

	// 中序遍历
	private class Iter implements Iterator<E> {

		List<E> list = null;
		int i = 0;

		public Iter() {
			list = new ArrayList<E>();
			work(list, root);
		}

		private void work(List list, Node node) {
			if (node != null) {
				work(list, node.left);
				list.add(node.data);
				work(list, node.right);
			}
		}

		@Override
		public boolean hasNext() {
			return !(i == list.size());
		}

		@Override
		public E next() {
			return list.get(i++);
		}
	}

}
