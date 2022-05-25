package leetcode;

/**
 * @author: mahao
 * @date: 2020/6/4
 */
public class T238 {
    static class Solution {
        public int[] productExceptSelf(int[] nums) {

            int[] L = new int[nums.length];
            L[0] = 1;//L和R数组存储的是左边和右边的乘积
            int[] R = new int[nums.length];
            R[nums.length - 1] = 1;

            for (int i = 1; i < nums.length; i++) {
                L[i] = L[i - 1] * nums[i-1];
                R[nums.length - i - 1] = R[nums.length - i] * nums[nums.length - i];
            }
            for (int i = 0; i < nums.length; i++) {
                nums[i] = L[i] * R[i];
            }
            return nums;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] ints = solution.productExceptSelf(new int[]{1, 2, 3});


    }
}
