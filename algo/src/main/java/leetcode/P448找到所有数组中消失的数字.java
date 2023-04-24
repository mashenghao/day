package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 位置移动
 *
 * @author mahao
 * @date 2023/04/20
 */
public class P448找到所有数组中消失的数字 {


    public static void main(String[] args) {
        List<Integer> disappearedNumbers = findDisappearedNumbers(new int[]{1, 1});
        System.out.println(disappearedNumbers);
    }

    public List<Integer> findDisappearedNumbers2(int[] nums) {
        int n = nums.length;
        for (int num : nums) {
            int x = (num - 1) % n;
            nums[x] += n;
        }
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] <= n) {
                ret.add(i + 1);
            }
        }
        return ret;
    }


    public static List<Integer> findDisappearedNumbers(int[] nums) {
//        输入：nums = [4,3,2,7,8,2,3,1]
        //   [       3]
//        输出：[5,6]
        for (int i = 0; i < nums.length; i++) {
            int value = nums[i];
            while (nums[value - 1] != value) {
                int a = nums[value - 1];
                nums[value - 1] = value;
                value = a;
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                list.add(i + 1);
            }
        }
        return list;
    }
}
