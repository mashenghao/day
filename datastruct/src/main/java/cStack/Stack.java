package cStack;

public class Stack {

	private int defaultSize = 10;

	protected Object[] objs;
	protected int top;// top的值是下一个要入栈的元素下标

	public Stack() {
		objs = new Object[10];
		top = 0;
	}

	public boolean isEmpty() {
		return top == 0;
	}

	public boolean isFull() {
		return top == defaultSize;
	}

	public void initStack() {
		top = 0;
	}

	public void push(Object obj) {
		if (isFull())
			System.out.println("栈满---");
		objs[top++] = obj;
	}

	public Object pop() {
		if (isEmpty()) {
			System.out.println("栈空-----");
			return null;
		}
		return objs[--top];
	}

	public Object getTop() {
		if (isEmpty()) {
			System.out.println("栈空-----");
			return null;
		}
		return objs[top - 1];
	}
}
