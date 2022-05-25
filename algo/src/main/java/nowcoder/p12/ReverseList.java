package nowcoder.p12;

/**
 * 反转链表：
 * 输入一个链表，反转链表后，输出新链表的表头。
 */
public class ReverseList {

    /**
     * 思路：
     * 头插法新建链表
     *
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {

        if (head == null) {
            return null;
        }
        ListNode nullFirst = new ListNode(-1);
        ListNode p = head, q;
        while (p != null) {
            q = p.next;
            p.next = nullFirst.next;
            nullFirst.next = p;
            p = q;
        }
        return nullFirst.next;
    }

    /**
     * 反转链表2，直接使用节点逆序
     *
     * @param head
     * @return
     */
    public ListNode ReverseList2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode q = head, p;
        p = q.next;
        q.next = null;
        ListNode r;
        while (p != null) {
            r = p.next;
            p.next = q;
            q = p;
            p = r;
        }
        return q;
    }
}


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}