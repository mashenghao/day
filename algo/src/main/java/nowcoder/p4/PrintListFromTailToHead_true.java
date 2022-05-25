package nowcoder.p4;

import java.util.ArrayList;

/**
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 * <p>
 * 思路：
 * 利用递归，实现链表的逆序输出
 */
public class PrintListFromTailToHead_true {

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = null;
        if (listNode == null) {
            return new ArrayList<>();
        } else {
            if (listNode.next != null) {
                list = printListFromTailToHead(listNode.next);
                list.add(listNode.val);
            } else {
                list = new ArrayList<>();
                list.add(listNode.val);
            }
        }
        return list;
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
        ArrayList<Integer> list = new PrintListFromTailToHead_true().printListFromTailToHead(l1);
        System.out.println(list);

    }
}
