package eTree;

public class Node<E> {

	public Object data;
	public Node<E> parent;
	public Node<E> left;
	public Node<E> right;

	public Node(Object data, Node parent) {
		this(data, parent, null, null);
	}

	public Node(Object data, Node left, Node right) {
		this(data, null, left, right);
	}

	public Node(Object data, Node parent, Node left, Node right) {
		this.data = data;
		this.parent = parent;
		this.left = left;
		this.right = right;
	}

}
