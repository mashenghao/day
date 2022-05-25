package nowcoder.p15;

/**
 * 操作给定的二叉树，将其变换为源二叉树的镜像。
 */
public class Mirror {

    public void Mirror(TreeNode root) {

        if (root == null) {
            return;
        }
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        Mirror(root.left);
        Mirror(root.right);
    }

    public void Mirror_true(TreeNode root) {
        if (root == null || root.right == null && root.left == null) {
            return;
        }
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }


    }
}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}