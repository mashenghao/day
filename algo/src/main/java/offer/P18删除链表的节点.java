package offer;

import java.util.List;

/**
 * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
 * <p>
 * 返回删除后的链表的头节点。
 * <p>
 * 输入: head = [4,5,1,9], val = 5<br>
 * 输出: [4,1,9]<br>
 * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
 * <p>
 *
 * @author mahao
 * @date 2022/08/13
 */
public class P18删除链表的节点 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode head2 = new ListNode(2);
        ListNode head3 = new ListNode(3);
        head.next = head2;
        head2.next = head3;
        ListNode listNode = new Solution().deleteNode(head, 3);
        List<Integer> list = ListNode.toList(listNode);
        System.out.println(list);
    }

    static class Solution {
        public ListNode deleteNode(ListNode head, int val) {
            if (head.val == val) {
                return head.next;
            }
            ListNode prev = head, p = head.next;
            while (p != null) {
                if (p.val == val) {
                    prev.next = p.next;
                    break;
                }
                prev = p;
                p = p.next;
            }
            return head;
        }
    }
}
