package offer;

import java.util.List;

/**
 * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
 * <p>
 * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
 * <p>
 * <p>
 * 示例：
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 k = 2.
 * <p>
 * 返回链表 4->5.
 *
 * @author mahao
 * @date 2022/08/13
 */
public class P22链表中倒数第K个节点 {

    public static void main(String[] args) {
        ListNode h1 = new ListNode(1);
        ListNode h2 = new ListNode(2);
        ListNode h3 = new ListNode(3);
        ListNode h4 = new ListNode(4);
        ListNode h5 = new ListNode(5);
        h1.next = h2;
        h2.next = h3;
        h3.next = h4;
        h4.next = h5;
        ListNode kthFromEnd = new Solution().getKthFromEnd(h1, 2);
        List<Integer> list = ListNode.toList(kthFromEnd);
        System.out.println(list);
    }

    static class Solution {
        public ListNode getKthFromEnd(ListNode head, int k) {
            ListNode fp = head, sp = head;
            while (k > 0 && fp != null) {
                fp = fp.next;
                k--;
            }
            while (fp != null) {
                fp = fp.next;
                sp = sp.next;
            }
            return sp;
        }
    }
}
