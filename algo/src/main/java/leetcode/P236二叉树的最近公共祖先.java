package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * <p>
 * 用后续遍历递归实现
 *
 * @author mahao
 * @date 2023/04/20
 */
public class P236二叉树的最近公共祖先 {


    //后续遍历实现
    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        //1. 如果根节点等于目标值， 说明递归可以在这里结束了。 即使root下面还有节点为 p 或 q， 但是函数是找公共父节点，一定是root。
        if (root.val == p.val || root.val == q.val) {
            return root;
        }

        TreeNode node = lowestCommonAncestor2(root.left, p, q);
        TreeNode node2 = lowestCommonAncestor2(root.right, p, q);
        //2. 在左右两边
        if (node2 != null && node != null) {
            return root;
        }

        //3.  这种情况被1 给处理掉了。  不会是当前root 为一个节点， 再从子节点找到另一个节点的情况，因为1直接返回了，如果root节点是
        //目标节点的话。
//        if ((root.val == p.val || root.val == q.val) && (node != null || node2 != null)) {
//            return root;
//        }

        if (node != null) {
            return node;
        }
        if (node2 != null) {
            return node2;
        }
        return null;
    }


    //根节点到两个节点的路径 去取交集，  交集最后的元素为最近公共祖先
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathP = pathToNode(root, p);
        List<TreeNode> pathQ = pathToNode(root, q);
        TreeNode node = root;
        for (int i = 0; i < Math.min(pathQ.size(), pathP.size()); i++) {
            if (pathP.get(i) == pathQ.get(i)) {
                node = pathP.get(i);
            } else {
                break;
            }
        }
        return node;
    }


    public static List<TreeNode> pathToNode(TreeNode root, TreeNode p) {
        List<TreeNode> path = new ArrayList<>();
        dfs(root, p, path);
        return path;
    }

    private static boolean dfs(TreeNode parent, TreeNode p, List<TreeNode> path) {

        path.add(parent);

        if (parent.val == p.val) {
            return true;
        }

        if (parent.left != null) {
            boolean dfs = dfs(parent.left, p, path);
            if (dfs) {
                return true;
            }
        }

        if (parent.right != null) {
            boolean dfs = dfs(parent.right, p, path);
            if (dfs) {
                return true;
            }
        }

        path.remove(path.size() - 1);

        return false;
    }


    public static void main(String[] args) {
        TreeNode n0 = new TreeNode(0);
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n2.left = n7;
        n2.right = n4;
        n1.left = n0;
        n1.right = n8;

//        List<TreeNode> list = lowestCommonAncestor(n3, new TreeNode(0));
        System.out.println(lowestCommonAncestor2(n3, n5, n1));

    }

}
