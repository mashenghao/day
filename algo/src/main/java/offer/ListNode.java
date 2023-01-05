package offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mahao
 * @date 2022/08/13
 */
public class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public static List<Integer> toList(ListNode head) {
        if (head == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list;
    }
}
