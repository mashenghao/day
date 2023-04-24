package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/lru-cache
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author mahao
 * @date 2023/04/18
 */
public class p146LRU缓存 {

    private Map<Integer, Node> map = new HashMap<>();
    int cap;
    //带头结点的链表。
    private Node head;
    private Node tail;

    public p146LRU缓存(int capacity) {
        this.cap = capacity;
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        //链表接起来
        head.after = tail;
        tail.before = head;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node == null) {
            Node newNode = new Node(key, value);
            //插入一个新元素
            addHead(newNode);
            map.put(key, newNode);
            if (map.size() > cap) {
                delTail();
            }
        } else {
            //更新操作
            node.value = value;
            moveHead(node);
        }
    }

    private void delTail() {
        Node removeNode = tail.before;

        //删除最后节点
        removeNode.before.after = tail;
        tail.before = removeNode.before;

        map.remove(removeNode.key);
    }

    /**
     * 当节点被在此访问时。
     *
     * @param node
     */
    private void moveHead(Node node) {
        //先断开node
        Node before = node.before;
        Node after = node.after;
        before.after = after;
        after.before = before;

        //插入头部
        node.after = head.after;
        node.before = head;
        head.after.before = node;
        head.after = node;

    }

    /**
     * 当新节点插入时
     *
     * @param node
     */
    private void addHead(Node node) {
        node.before = head;
        node.after = head.after;

        head.after.before = node;
        head.after = node;
    }

    private List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        Node p = head.after;
        while (p != tail) {
            list.add(p.value);
            p = p.after;
        }
        return list;
    }

    static class Node {
        Node before;
        Node after;
        int key;
        int value;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }


    public static void main(String[] args) {
        p146LRU缓存 lRUCache = new p146LRU缓存(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache.get(1);    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lRUCache.get(2);    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        lRUCache.get(1);    // 返回 -1 (未找到)
        lRUCache.get(3);    // 返回 3
        lRUCache.get(4);    // 返回 4


    }
}
