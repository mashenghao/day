package nowcoder.p4;

import java.util.ArrayList;

/**
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 * 思路： 先反转链表，然后顺序添加
 */
public class PrintListFromTailToHead {

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode == null)
            return new ArrayList<>();
        listNode = resver(listNode);
        ArrayList<Integer> list = new ArrayList<>();
        while (listNode != null) {
            list.add(listNode.val);
            listNode = listNode.next;
        }
        return list;
    }

    /**
     * 反转listnode:
     * <p>
     * 通过链表的头插法：
     *
     * @param listNode
     */
    public ListNode resver(ListNode listNode) {
        if (listNode == null)
            return null;
        ListNode p1, p2, p3;
        p1 = listNode;
        p2 = p1.next;
        p1.next = null;
        while (p2 != null) {
            p3 = p2.next;
            p2.next = p1;
            p1 = p2;
            p2 = p3;
        }
        return p1;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = null;
        ListNode l5 = new PrintListFromTailToHead().resver(l1);
        System.out.println(l5);
    }
}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                '}';
    }
}
