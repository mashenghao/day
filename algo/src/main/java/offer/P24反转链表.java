package offer;

/**
 * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
 *
 * @author mahao
 * @date 2022/08/13
 */
public class P24反转链表 {

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
        ListNode listNode = new Solution().reverseList(h1);
        System.out.println(ListNode.toList(listNode));
    }

    static class Solution {
        //头插法
        public ListNode reverseList(ListNode head) {
            ListNode revert = null;
            ListNode p;
            while (head != null) {
                p = head.next;
                head.next = revert;
                revert = head;
                head = p;
            }
            return revert;
        }
    }
}
