package leetcode;

import java.util.Arrays;

/**
 * @author mash
 * @date 2024/3/17 15:50
 */
public class P2221 {

    static class As {

        public static void main(String[] args) {
            triangularSum(new int[]{1, 2, 3, 4, 5});
        }

        public static int triangularSum(int[] nums) {
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < nums.length - i; j++) {
                    int re = (nums[j] + nums[j + 1]) % 10;
                    nums[j] = re;
                }
                System.out.println(Arrays.toString(nums));
            }

            return nums[0];
        }
    }
}
