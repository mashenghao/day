package acmcoder;


import java.util.Scanner;

/**
 * 给定一个单向链表和一个整数m，
 * 将链表中小于等于m的节点移到大于m的节点之前，要求两部分中的节点各自保持原有的先后顺序
 */
public class Main {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /*请完成下面这个函数，实现题目要求的功能
     ******************************开始写代码******************************/

    /**
     * 4
     * 9 6 3 7 6 5
     * 3,9,6,7,6,5
     *
     * @param head
     * @param m
     * @return 8 9 10 1
     */
    static ListNode partition(ListNode head, int m) {
        ListNode minFirst = new ListNode(-1), minP = null;
        minP = minFirst;
        ListNode maxFirst = new ListNode(-1), maxP = null;
        maxP = maxFirst;
        ListNode p = head;
        while (p != null) {
            if (p.val <= m) {
                minP.next = p;
                minP = p;
            } else {
                maxP.next = p;
                maxP = p;
            }
            p = p.next;
        }
        minP.next = maxFirst.next;
        if (maxP != null)
            maxP.next = null;
        return minFirst.next;
    }

    /******************************结束写代码******************************/


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ListNode head = null;
        ListNode node = null;
        int m = in.nextInt();
        while (in.hasNextInt()) {
            int v = in.nextInt();
            if (head == null) {
                node = new ListNode(v);
                head = node;
            } else {
                node.next = new ListNode(v);
                node = node.next;
            }
        }
        head = partition(head, m);
        if (head != null) {
            System.out.print(head.val);
            head = head.next;
            while (head != null) {
                System.out.print(",");
                System.out.print(head.val);
                head = head.next;
            }
        }
        System.out.println();
    }
}
