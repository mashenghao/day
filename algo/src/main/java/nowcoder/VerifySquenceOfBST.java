package nowcoder;

/**
 * 二叉搜索树的后序遍历序列：
 * <p>
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
 * 如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
 */
public class VerifySquenceOfBST {

    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        }
        int n, j;
        for (int i = sequence.length - 1; i > 0; i--) {

            n = sequence[i];
            j = i - 1;
            while (j >= 0 && sequence[j] > n) {
                j--;
            }
            while (j >= 0 && sequence[j] < n) {
                j--;
            }
            if (j != -1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        VerifySquenceOfBST v = new VerifySquenceOfBST();
        int[] arr = {3, 7, 5, 10, 17, 15, 11};
        System.out.println(v.VerifySquenceOfBST(arr));
    }
}
