package nowcoder;

import java.util.HashMap;

/**
 * 复杂链表的复制：
 * <p>
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
 * 返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 */
public class Clone {

    public RandomListNode Clone(RandomListNode pHead) {

        RandomListNode head = new RandomListNode(-1);
        RandomListNode p, l;
        p = head;
        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();

        while (pHead != null) {

            RandomListNode newNode = map.get(pHead);
            if (newNode == null) {
                newNode = new RandomListNode(pHead.label);
                map.put(pHead, newNode);
            }


            if (pHead.random != null) {
                RandomListNode randomListNode = map.get(pHead.random);
                if (randomListNode == null) {
                    randomListNode = new RandomListNode(pHead.random.label);
                    map.put(pHead.random, randomListNode);
                }
                newNode.random = randomListNode;
            }

            p.next = newNode;
            p = p.next;
            pHead = pHead.next;
        }
        return head.next;
    }

    /**
     * 1.将链表节点，复制到链表之后，比如A的复制节点A1，使 A.next = A1;
     * 2.循环一遍，使复制节点的random指向random的复制节点，A.next.random = A.random
     * 3.选取复制节点。
     */
    public RandomListNode Clone2(RandomListNode pHead) {

        if (pHead == null)
            return null;

        RandomListNode p = pHead;

        while (p != null) {//将A1节点，放在A之后
            RandomListNode node = new RandomListNode(p.label);
            node.next = p.next;
            p.next = node;
            p = node.next;
        }

        p = pHead;
        while (p != null) {
            RandomListNode node = p.next;
            if (p.random != null) {
                node.random = p.random.next;
            }
            p = node.next;
        }

        RandomListNode pCloneHead = pHead.next;
        RandomListNode tmp;
        p = pHead;
        while (p.next != null) {
            tmp = p.next;
            p.next = tmp.next;
            p = tmp;
        }
        return pCloneHead;
    }
}

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}