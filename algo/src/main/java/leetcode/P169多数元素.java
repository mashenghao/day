package leetcode;

/**
 * @author mahao
 * @date 2023/04/18
 */
public class P169多数元素 {


    public static void main(String[] args) {
        int[] arr = new int[]{3, 2, 3};
        int i = majorityElement(arr);
        System.out.println(i);
    }

    public static int majorityElement(int[] nums) {
        int num = nums[0];
        int isNumCount = 1;
        for (int i = 1; i < nums.length; i++) {
            if (num == nums[i]) {
                isNumCount++;
            } else {
                isNumCount--;
            }
            if (isNumCount == 0) {
                isNumCount = 1;
                num = nums[i];
            }
        }
        return num;
    }
}
