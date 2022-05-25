package nowcoder;

import java.util.Stack;

/**
 * 包含min函数的栈：
 * <p>
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
 * <p>
 * 思路：
 * 通过创建一个辅助栈，辅助栈中的数据个数，和数据栈中的数据个数是一样的，只是，辅助栈存储的数据，
 * 是当前数据里面最小的数。比如一开始存入2 ，则两个栈都放2 ，下一次存3，则数据栈，存入3，辅助栈中任然存储2
 */
public class MinStack {

    Stack<Integer> dataStack = new Stack<>();
    Stack<Integer> minStack = new Stack<>();

    public void push(int node) {
        dataStack.push(node);
        minStack.empty();
        if (minStack.empty()) {
            minStack.push(node);
        } else {
            Integer peek = minStack.peek();
            if (peek < node) {
                minStack.push(peek);
            } else {
                minStack.push(node);
            }
        }
    }

    public void pop() {
        dataStack.pop();
        minStack.pop();
    }

    public int top() {

        return dataStack.peek();
    }

    public int min() {

        return minStack.peek();
    }
}
