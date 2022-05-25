package basic.source.CLinkedList;

import java.util.Deque;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/**
 * LinkedList源码分析
 */
public class MainClass {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        //list操作
        list.add("a");
        list.add("c");
        list.add(1, "b");
        list.add(3, "e");
        list.remove("e");
        ListIterator<String> it = list.listIterator(list.size());
        while (it.hasPrevious()) {
            String s = it.previous();
            System.out.println(s + " ");
        }

        //队列操作 --头出尾进
        System.out.println("*********************queue");
        Queue<String> queue = list;
        String peek = queue.peek();//返回队列头，
        System.out.println(peek);
        String e = queue.poll();//和poll()相等
        System.out.println(e);
        queue.offer("e"); //压入元素
        System.out.println(queue);

        //双端队列
        Deque<String> deque = (Deque<String>) queue;
    }
}
