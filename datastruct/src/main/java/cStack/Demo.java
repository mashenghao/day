package cStack;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Demo {

	private static final Map<Character, Integer> OPS;

	private static final char table[][] = { //
			{ '>', '>', '<', '<', '<', '>', '>' }, // +
			{ '>', '>', '<', '<', '<', '>', '>' }, // -
			{ '>', '>', '>', '>', '<', '>', '>' }, // *
			{ '>', '>', '>', '>', '<', '>', '>' }, // /
			{ '<', '<', '<', '<', '<', '=', ' ' }, // (
			{ '>', '>', '>', '>', ' ', '>', '>' }, // )
			{ '<', '<', '<', '<', '<', ' ', '=' }, // #
	};

	static {
		OPS = new HashMap<>();
		OPS.put('+', 0);
		OPS.put('-', 1);
		OPS.put('*', 2);
		OPS.put('/', 3);
		OPS.put('(', 4);
		OPS.put(')', 5);
		OPS.put('#', 6);
	}

	public static void main(String[] args) {
		new Demo().demo3();
	}

	// 表达式求值4+5*6+(5-1*4)
	@Test
	public void demo3() {

		char[] data = "4-5*6+(1-1*4)#".toCharArray();

		// 1.定义两个栈，数字栈，运算符栈 ，运算符栈初始化为#
		Stack numS = new Stack();
		Stack optS = new Stack();
		optS.push('#');
		int i = 0;
		// 2.循环读取字符，循环条件为运算符栈顶不是#||新读取的字符也不是#（运算符只剩#，结束标记符）
		while ((char) optS.getTop() != '#' || data[i] != '#') {
			char c = data[i];
			// 3.如果是数字，压入数字栈中，读取下一个字符
			if (isNum(c)) {
				numS.push(c);
				i++;
			} else {// 4.是运算符，就取栈顶，和当前运算符比较
				char top = (char) optS.getTop();
				char flag = compareOpt(top, c);
				if (flag == '>') {
					// 4.1 > 取栈顶运算符，和两个数字，进行运算，结果压入数字栈
					int result = calculate((char) optS.pop(), new Integer(numS.pop() + ""),
							new Integer(numS.pop() + ""));
					numS.push(result);
					System.out.println(result);
				} else if (flag == '=') {
					// 4.2 = 就是两个（），（3）这种形式的，脱括号，弹出运算符栈顶，读取下个字符
					optS.pop();
					i++;
				} else {
					// 4.3 < 直接压入，读取下一个
					optS.push(c);
					i++;
				}
			}
		}
		// 2.2 运算结束，取出来栈顶结果
		System.out.println(numS.getTop());

	}

	@Test
	public void testmethod() {
		double result = calculate('/', 3, 5);
		System.out.println(result);
	}

	// 计算 num2 opt num1
	private int calculate(char opt, int num1, int num2) {
		System.out.print(num2 + " " + opt + " " + num1 +" = ");
		switch (opt) {
		case '+':
			return num2 + num1;
		case '-':
			return num2 - num1;
		case '*':
			return num2 * num1;
		case '/':
			return num2 / num1;
		default:
			throw new RuntimeException("无法计算");
		}

	}

	// 比较两个运算符的优先级
	private char compareOpt(char top, char c) {
		int l = OPS.get(top);
		int r = OPS.get(c);
		return table[l][r];
	}

	private boolean isNum(char c) {
		return c >= '0' && c <= '9';
	}

	/********************************************************/

	@Test
	public void demo4() {
		Stack optS = new Stack();

	}

	// 进制转换
	@Test
	public void demo2() {
		LinkedStack stack = mod(5, 2);
		Object pop;
		while ((pop = stack.pop()) != null) {
			System.out.println(pop);
		}
	}

	private LinkedStack mod(int num, int modNum) {
		LinkedStack s = new LinkedStack();
		/*
		 * do { int n = num % modNum;// s.push(n);// } while ((num =num/ modNum) != 0);
		 */
		while (num != 0) {
			s.push(num % modNum);
			num = num / modNum;
		}
		return s;
	}

	// 链表结构栈
	@Test
	public void demo1() {
		LinkedStack s = new LinkedStack();
		s.push(1);
		s.push(2);
		s.push(3);
		Object pop;
		while ((pop = s.pop()) != null) {
			System.out.println(pop);
		}
	}
}

class LinkedStack {

	private Node top;

	LinkedStack() {
		top = null;
	}

	public void push(Object obj) {
		Node node = new Node(obj, top);
		top = node;
	}

	public Object pop() {
		if (top == null) {
			System.out.println("栈空");
			return null;
		}
		Object obj = top.data;
		top = top.next;
		return obj;
	}

	private class Node {
		Object data;
		Node next;

		public Node(Object data, Node next) {
			super();
			this.data = data;
			this.next = next;
		}

	}
}