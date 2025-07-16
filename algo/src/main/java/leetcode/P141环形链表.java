package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 *判断链表中是否有环：
 *
 * 快慢指针解法，一个走2步，一个走一步，如果有环，一定会相遇的。
 *
 *  1 2
 *
 *
 *
 * @author mash
 * @date 2024/10/20 下午4:52
 */
public class P141环形链表 {

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    class Solution {

        public boolean hasCycle(ListNode head) {
            if (head == null) {
                return false;
            }

            ListNode slow = head;
            ListNode fast = head;
            while (slow != null && fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (fast == slow) {
                    return true;
                }
            }

            return false;
        }

        /**
         * 第一个出现的环形链表节点：
         * f=2s （快指针每次2步，路程刚好2倍）
         *
         * f = s + nb (相遇时，刚好多走了n圈）
         *
         * 推出：s = nb
         *
         * 从head结点走到入环点需要走 ： a + nb， 而slow已经走了nb，那么slow再走a步就是入环点了。
         *
         * 如何知道slow刚好走了a步？ 从head开始，和slow指针一起走，相遇时刚好就是a步
         *
         * @param head
         * @return
         */
        public ListNode detectCycle(ListNode head) {
            if (head == null) {
                return null;
            }
            ListNode slow = head;
            ListNode fast = head;
            while (slow != null && fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (fast == slow) {
                    //有环， 慢指针走的圈数 *  环数量 = 慢指针走的步数， 需要慢指针在走，相与的地方就是入口
                    fast = head;
                    while (fast != slow) {
                        fast = fast.next;
                        slow = slow.next;
                    }
                    return slow;
                }
            }

            return null;
        }
    }

}
