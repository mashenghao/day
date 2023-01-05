package offer;

/**
 * @author mahao
 * @date 2022/08/11
 */
public class P4 {

    public static void main(String[] args) {

    }


    public static int findRepeatNumber(int[] nums) {
        for (int i = 0; i < nums.length; ) {
            int idxValue = nums[i];
            if (idxValue == i) {
                i++;
                continue;
            } else if (idxValue == nums[idxValue]) {
                return idxValue;
            } else {
                int t = nums[idxValue];
                nums[idxValue] = idxValue;
                nums[i] = t;
            }
        }
        return -1;
    }

    public static int minArray(int[] numbers) {
        int min = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (min > numbers[i]) {
                min = numbers[i];
                return min;
            }
        }
        return min;
    }
}
