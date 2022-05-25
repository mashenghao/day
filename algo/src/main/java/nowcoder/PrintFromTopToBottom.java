package nowcoder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * 从上往下打印二叉树:
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 * <p>
 * 思路：
 * 借助队列实现，用队列存储根节点，然后判断队列是不是空，
 * 将根节点遍历，然后在存储左右子节点，一直循环。
 */
public class PrintFromTopToBottom {

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();

        queue.add(root);
        TreeNode p;
        while (!queue.isEmpty()) {
            p = queue.poll();
            list.add(p.val);
            if (p.left != null) {
                queue.add(p.left);
            }
            if (p.right != null) {
                queue.add(p.right);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        PrintFromTopToBottom p = new PrintFromTopToBottom();
        TreeNode root = new TreeNode();
        ArrayList<Integer> list = p.PrintFromTopToBottom(null);
        System.out.println(list);
    }
}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

    public TreeNode() {

    }
}