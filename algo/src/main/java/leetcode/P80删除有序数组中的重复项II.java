package leetcode;

/**
 * 给你一个有序数组 nums ，
 * 请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
 *
 * @author mahao
 * @date 2023/04/17
 */
public class P80删除有序数组中的重复项II {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1, 2, 2, 3};
        int i = removeDuplicates(nums);
        for (int j = 0; j < i; j++) {
            System.out.print(nums[j] + "  ");
        }
    }

    /**
     * 输入：nums = [1,1,1,2,2,3]
     * 输出：5, nums = [1,1,2,2,3]
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }
        int val0 = nums[0];
        int val1 = nums[1];
        int isVal = 0;
        for (int i = 2; i < nums.length; i++) {
            nums[i - isVal] = nums[i];
            if (val0 == val1 && val1 == nums[i]) {
                isVal++;
            }
            val0 = val1;
            val1 = nums[i];
        }
        return nums.length - isVal;
    }
}
