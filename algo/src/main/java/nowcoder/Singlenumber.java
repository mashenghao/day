package nowcoder;

import java.util.Scanner;

/**
 * 一行数字，输出只出现一个数字
 *
 * @author: mahao
 * @date: 2019/9/12
 */
public class Singlenumber {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] nums = in.next().split(",");
        int re = 1;
        for (int i = 1; i < nums.length; i++) {
            if (Integer.valueOf(nums[i - 1]) - Integer.valueOf(nums[i]) != 0) {
                re++;
            }
        }
        System.out.println(re);
    }
}
