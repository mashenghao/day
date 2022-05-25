package nowcoder;

import java.util.Stack;

/**
 * 栈的压入、弹出序列：
 * <p>
 * 输入两个整数序列，第一个序列表示栈的压入顺序，
 * 请判断第二个序列是否可能为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。
 * 例如序列1,2,3,4,5是某栈的压入顺序，
 * 序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
 * 但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
 */
public class IsPopOrder {

    /*
     * 1 2 3 4 5
     *
     * 4 5 3 2 1
     *
     * 4 3 5 1 2
     *
     */
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        int p = 0;
        int q = 0;
        Stack<Integer> stack = new Stack<>();

        while ((p < pushA.length || !stack.empty()) && q < popA.length) {
            int current = popA[q];
            if (!stack.empty() && current == stack.peek()) {//栈顶元素和当前元素一直，出栈，popA前进一位
                stack.pop();
                q++;
            } else {//不一样，stack进栈
                if (p >= pushA.length) {
                    return false;
                }
                stack.push(pushA[p]);
                p++;
            }
        }

        if (p == pushA.length && q == popA.length && stack.empty()) {
            return true;
        }
        return false;
    }


    public boolean IsPopOrder2(int[] pushA, int[] popA) {

        Stack<Integer> s = new Stack<>();
        int index = 0;
        for (int i = 0; i < pushA.length; i++) {
            s.push(pushA[i]);
            while (!s.empty() && s.peek() == popA[index]) {
                s.pop();
                index++;
            }
        }
        return s.empty();
    }

    public static void main(String[] args) {
        IsPopOrder isPopOrder = new IsPopOrder();
        int[] pushA = {1, 2, 3};
        int[] popA = {3, 2, 1};
        boolean b = isPopOrder.IsPopOrder2(pushA, popA);
        System.out.println(b);
    }
}
