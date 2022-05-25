package leetcode;

/**
 * @author: mahao
 * @date: 2019/9/21
 */
public class T206 {

    public ListNode reverseList(ListNode head) {
        ListNode newHead = new ListNode(-1);
        ListNode p = head;
        while (p != null) {
            head = p.next;
            p.next = newHead.next;
            newHead.next = p;
            p = head;
        }
        return newHead.next;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}