package nowcoder.p2;

import java.util.Stack;

/**
 * 两个栈，完成队列的出栈入栈操作
 * 思路：
 * 两个栈分开使用，一个栈stack1 入栈操作，另一个栈stack2出栈操作，
 * 入栈：stack1检查栈满，如果栈满，判断stack2是否栈空，栈空出栈到Stack2，stack2入栈
 * 出栈：stack2出栈，栈顶出栈，如果stack为空，从stack1入栈操作。
 */
public class TwoStackToQueue {

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.isEmpty()) {
            switchStack();
        }
        return stack2.pop();
    }

    public void switchStack() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
    }

    public static void main(String[] args) {
        TwoStackToQueue t = new TwoStackToQueue();
        
    }
}
