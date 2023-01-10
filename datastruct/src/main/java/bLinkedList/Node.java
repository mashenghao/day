package bLinkedList;

public class Node {

	Object item;
	Node next;
	Node prev;

	public Node(Node prev, Object item, Node next) {
		super();
		this.item = item;
		this.next = next;
		this.prev = prev;
	}
}
