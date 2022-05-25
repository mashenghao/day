package nowcoder.p11;


/**
 * 输入一个链表，输出该链表中倒数第k个结点。
 */
public class FindKthToTail {

    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k < 1  ) {
            return  null;
        }

        ListNode p1, p2;
        p1 = p2 = head;
        for (int i = 0; i < k - 1; i++) {
            if (p2.next != null) {
                p2 = p2.next;
            } else {
                return null;
            }
        }
        while (p2.next != null) {
            p2 = p2.next;
            p1 = p1.next;
        }

        return p1;

    }


    public static void main(String[] args) {
        FindKthToTail f = new FindKthToTail();
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(6);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = null;
        ListNode listNode = f.FindKthToTail(n1, 7);
        System.out.println(listNode.val);
    }
}


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}