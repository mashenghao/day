package nowcoder.p7;

/**
 * @program: algorithm
 * @Date: 2019/6/10 20:38
 * @Author: mahao
 * @Description: 重建二叉树
 */
public class ReConstructBinaryTree {


    public static void main(String[] args) {
        ReConstructBinaryTree r = new ReConstructBinaryTree();
        int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] in = {4, 7, 2, 1, 5, 3, 8, 6};
        TreeNode t = r.reConstructBinaryTree(pre, in);
        System.out.println(t);
    }

    public TreeNode reConstructBinaryTree(int[] a1, int[] a2) {
        if (a1 == null || a2 == null) {
            return null;
        }
        return buildBinaryTree(a1, a2, 0, a1.length - 1, 0, a1.length - 1);
    }

    private TreeNode buildBinaryTree(int[] a1, int[] a2, int l, int r, int l2, int r2) {

        if (l > r || l2 > r2) {
            return null;
        }
        int n = a1[l];//根节点的值
        TreeNode root = new TreeNode(n);
        for (int i = l2; i <= r2; i++) {
            if (n == a2[i]) {
                root.left = buildBinaryTree(a1, a2, l + 1, l + i - l2, l2, i - 1);
                root.right = buildBinaryTree(a1, a2, l + i + 1 - l2, r, i + 1, r2);
            }
        }
        return root;
    }


}
