package cStack;

/**
 * 栈 ： 先进后出
 *
 * @author mahao
 * @date: 2019年3月19日 下午8:16:45
 */
public class MainClass {

	public static void main(String[] args) {
		Stack s = new Stack();
		for (int i = 0; !s.isFull(); i++) {// 进栈
			s.push(i);
		}
		while (!s.isEmpty()) {// 出栈
			printf(s.pop());
		}
		Object top = s.getTop();// 取栈顶
		printf(top);
	}

	public static void printf(Object obj) {
		System.out.println(obj);
	}
}
