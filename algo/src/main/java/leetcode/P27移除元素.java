package leetcode;

import acmcoder.Main;

/**
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 * <p>
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/remove-element
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author mahao
 * @date 2023/04/17
 */
public class P27移除元素 {

    public static void main(String[] args) {
        int[] ints = {0,1,2,2,3,0,4,2};
        int i = removeElement(ints, 2);
        for (int j = 0; j < i; j++) {
            System.out.print(ints[j] + " ");
        }
    }

    /**
     * 输入：nums = [3,2,2,3], val = 3
     * 输出：2, nums = [2,2]
     * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
     * 你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案
     * <p>
     *[0,1,2,2,3,0,4,2]
     * 2
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        int isValNum = 0;
        for (int i = 0; i < nums.length; i++) {
            nums[i - isValNum] = nums[i];
            if (nums[i] == val) {
                isValNum++;
            }
        }
        return nums.length - isValNum;
    }

}
