package nowcoder.p13;


/**
 * 输入两个单调递增的链表，
 * 输出两个链表合成后的链表，
 * 当然我们需要合成后的链表满足单调不减规则。
 */
public class Merge {

    public ListNode Merge(ListNode list1, ListNode list2) {

        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        } else {
            ListNode nullFirst = new ListNode(-1);
            //p存储的是一个实际的地址值，一定要注意，现在p和nullFirst是同一个引用变量，指向的地址值
            //也是相同的，nullFirst用来记录地址值，p去添加变量。
            ListNode p = nullFirst;
            int num1, num2;
            while (list1 != null && list2 != null) {
                num1 = list1.val;
                num2 = list2.val;
                if (num1 <= num2) {
                    p.next = list1;
                    list1 = list1.next;
                } else {
                    p.next = list2;
                    list2 = list2.next;
                }
                p = p.next;
            }
            if (list1 != null) {
                p.next = list1;
            }
            if (list2 != null) {
                p.next = list2;
            }
            return nullFirst.next;
        }

    }

    //TODO： 可以用递归实现，尚未知道，二刷的时候再来解答
 /*   public ListNode Merge_true(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        } else {
            ListNode nullFirst = null;
            if (list1.val<=list2.val){
                nullFirst = Merge_true(list1,list2.next);
            }

        }
    }
*/
    public static void main(String[] args) {
        Merge m = new Merge();
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(6);
        n1.next = n3;
        n2.next = n4;
        n3.next = n5;
        n4.next = n6;
        n5.next = null;
        n6.next = null;
        ListNode first = m.Merge(n1, n3);
        System.out.println(first);
    }
}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
