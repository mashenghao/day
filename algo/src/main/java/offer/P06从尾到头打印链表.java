package offer;

import java.util.Arrays;

/**
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 *
 * @author mahao
 * @date 2022/08/13
 */
public class P06从尾到头打印链表 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode head2 = new ListNode(2);
        ListNode head3 = new ListNode(3);
        head.next = head2;
        head2.next = head3;
        int[] ints = new Solution().reversePrint(head);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }


    static class Solution {
        public int[] reversePrint(ListNode head) {
            ListNode revert = null;
            int i = 0;
            ListNode p = null;
            while (head != null) {
                p = head.next;
                head.next = revert;
                revert = head;
                head = p;
                i++;
            }
            int j = 0;
            int[] arr = new int[i];
            while (j < i && revert != null) {
                arr[j++] = revert.val;
                revert = revert.next;
            }
            return arr;
        }
    }

}
