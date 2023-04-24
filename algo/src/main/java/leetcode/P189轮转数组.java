package leetcode;

/**
 * 旋转数组
 *
 * @author mahao
 * @date 2023/04/18
 */
public class P189轮转数组 {


    public static void main(String[] args) {
        int[] arr = new int[]{-1, -100, 3, 99}; //   3 99 -1 -100
        int k = 2;
        rotate2(arr, k);
    }

//    public static void rotate3(int[] nums, int k) {
//        int len = nums.length;
//        k = k % len;
//
//        for (int i = 0; i < k; i++) {
//            int t = nums[0];
//            int idx = i;
//            while (idx != i) {
//                int newIdx = (idx + k) % len;
//                int a = nums[newIdx];
//                nums[newIdx] = t;
//                t = a;
//                idx = newIdx;
//            }
//        }
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
//    }

    public static void rotate2(int[] nums, int k) {
        int len = nums.length;
        int[] nums2 = new int[nums.length];
        k = k % len;
        for (int i = len - 1; i >= 0; i--) {
            nums2[(i + k) % len] = nums[i];
        }
        for (int i = 0; i < len; i++) {
            nums[i] = nums2[i];
        }
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }

    public static void rotate(int[] nums, int k) {
        int len = nums.length;
        k = k % len;
        for (int i = 0; i < k; i++) {
            int lastNum = nums[len - 1];
            for (int j = len - 1; j >= 1; j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = lastNum;
        }
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}
