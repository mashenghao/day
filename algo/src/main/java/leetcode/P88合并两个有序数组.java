package leetcode;

/**
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * <p>
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * <p>
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/merge-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author mahao
 * @date 2023/04/17
 */
public class P88合并两个有序数组 {
    /**
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/merge-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param args
     */
    public static void main(String[] args) {
        merge(new int[]{1}, 0, new int[]{1}, 1);
    }

    /**
     * 倒叙插入 nums1数组中
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int idx = m + n - 1;
        int i = m - 1;
        int j = n - 1;
        while (i >= 0 && j >= 0) {
            int i1 = nums1[i];
            int j1 = nums2[j];
            if (i1 >= j1) {
                nums1[idx] = i1;
                i--;
            } else {
                nums1[idx] = j1;
                j--;
            }
            idx--;
        }
        while (i >= 0) {
            nums1[idx--] = nums1[i--];
        }
        while (j >= 0) {
            nums1[idx--] = nums2[j--];
        }
        for (int i1 = 0; i1 < nums1.length; i1++) {
            System.out.print(nums1[i1] + " ");
        }
    }
}
