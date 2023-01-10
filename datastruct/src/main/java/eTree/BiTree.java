package eTree;

import cStack.Stack;

public class BiTree {

	private Node root;
	char[] chs;
	int i = 0;

	public BiTree(String str) {
		chs = str.toCharArray();
		root = crate1();
	}

	/** 先序创建 */
	public Node crate1() {
		char c = getChar();
		Node n = null;
		if (c == '#') {
			return null;
		} else {
			n = new Node(c, null);
			n.left = crate1();
			n.right = crate1();
		}
		return n;
	}

	/** 先序遍历 */
	public void printf1() {
		printf11(root);
	}

	private void printf11(Node node) {
		if (node != null) {
			System.out.print(node.data);
			printf11(node.left);
			printf11(node.right);
		}
	}

	/** 中序遍历 */
	public void printfCenter() {
		printfCenter1(root);
	}

	private void printfCenter1(Node node) {
		if (node != null) {
			printfCenter1(node.left);
			System.out.print(node.data);
			printfCenter1(node.right);
		}
	}

	/** 前序遍历非递归算法 */
	public void eachFirstr() {
		// 1.定义一个栈，用来存储节点

		// 2.当栈不空，节点也不空时
		// 2.1 节点不空
		// 输出节点数据
		// 进栈
		// 指针指向左孩子
		// 2.2节点为空时
		// 出栈
		// 指针指向出栈的右孩子
		Stack s = new Stack();
		Node p = root;
		while (p != null || !s.isEmpty()) {
			if (p != null) {
				System.out.print(p.data);
				s.push(p);
				p = p.left;
			} else {
				Node node = (Node) s.pop();
				p = node.right;
			}
		}
	}

	/** 后序遍历非递归算法 */
	public void eachLast() {
		Node h = root;
		if (h != null) {
			java.util.Stack<Node> stack = new java.util.Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				if (c.left != null && h != c.left && h != c.right) {
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {
					stack.push(c.right);
				} else {
					System.out.print(stack.pop().data + " ");
					h = c;
				}
			}
		}
	}

	/** 中序遍历非递归算法 */
	public void eachCenter() {
		// 1.定义一个栈，用来存储未遍历的节点
		// 2.1 从根节点遍历，当节点不为空
		// 节点进栈
		// 指针指向左孩子
		// 2.2节点为空，
		// 出栈，输出的出栈元素
		// 指针指向栈顶右孩子
		Stack s = new Stack();
		Node p = root;
		// p和s同是为空，为空二叉树或者遍历结束
		while (p != null || !s.isEmpty()) {
			// 2.1 从根节点遍历，当节点不为空
			// 节点进栈
			if (p != null) {
				s.push(p);
				p = p.left;
			} else {
				// 2.2节点为空，
				// 出栈，输出的出栈元素
				// 指针指向栈顶右孩子
				Node node = (Node) s.pop();
				System.out.print(node.data);
				p = node.right;
			}
		}
		// 2.1 从根节点遍历，当节点不为空
		// 节点进栈
		// 指针指向左孩子
		// 2.2节点为空，
		// 出栈，输出的出栈元素
		// 指针指向栈顶右孩子
	}

	private char getChar() {
		return chs[i++];
	}
}
