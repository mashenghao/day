package offer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 链接：https://leetcode.cn/problems/fu-za-lian-biao-de-fu-zhi-lcof
 * 请实现 copyRandomList 函数，复制一个复杂链表。
 * 在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，
 * 还有一个 random 指针指向链表中的任意节点或者 null。
 * <p>
 *
 * @author mahao
 * @date 2022/08/13
 */
public class P35复杂链表复制 {


    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    static class Solution {
        public Node copyRandomList(Node head) {
            //老节点 -> 新节点
            Map<Node, Node> map = new HashMap<>();
            Node newHead = new Node(-1);

            Node p = head;
            Node prev = newHead;
            while (p != null) {
                Node node = new Node(p.val);
                prev.next = node;
                map.put(p, node);

                p = p.next;
                prev = node;
            }

            //根据映射关系，求random
            p = head;
            while (p != null) {
                if (p.random != null) {
                    map.get(p).random = map.get(p.random);
                }
                p = p.next;
            }

            return newHead.next;
        }
    }
}
