package leetcode;

/**
 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
 * <p>
 * 由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。更规范地说，如果在删除重复项之后有 k 个元素，那么 nums 的前 k 个元素应该保存最终结果。
 * <p>
 * 将最终结果插入 nums 的前 k 个位置后返回 k 。
 * <p>
 * 不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/remove-duplicates-from-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author mahao
 * @date 2023/04/17
 */
public class P26删除有序数组中的重复项 {


    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int i = removeDuplicates(nums);
        for (int j = 0; j < i; j++) {
            System.out.print(nums[j] + " ");
        }
    }


    /**
     * 输入：nums = [1,1,2]
     * 输出：2, nums = [1,2,_]
     * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2
     * <p>
     * <p>
     * jw
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        int val = nums[0];
        int isVal = 0;
        for (int i = 1; i < nums.length; i++) {
            nums[i - isVal] = nums[i];
            if (nums[i] == val) {
                isVal++;
            }
            val = nums[i];
        }
        return nums.length - isVal;
    }
}
