package nowcoder;

import java.util.Scanner;

/**
 * 分饼干
 *
 * @author: mahao
 * @date: 2019/9/12
 */
public class Five8_2 {
    /**
     * int ans = 0, b = 0, lastScore = 0;
     * int score = 0;
     * boolean isMax = true;//表示已经加了
     * for (int i = 0; i < n; i++) {
     * score = sc.nextInt();
     * if (score > lastScore) {
     * b++;
     * ans += b;
     * isMax = true;
     * // System.out.println("--  "+b);
     * } else {
     * if (isMax == false) {
     * ans++;
     * }
     * ans++;
     * b = 1;
     * isMax = false;
     * //System.out.println("-----  "+1);
     * }
     * lastScore = score;
     * }
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n + 1];
        int[] flags = new int[n + 1];
        int lastScore = 0;
        int sum = 0;
        int b = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
            if (nums[i] > lastScore) {
                b++;
            } else {
                b = 1;
            }
            sum += b;
            lastScore = nums[i];
            flags[i] = b;
        }
       // System.out.println(sum);
        lastScore = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > lastScore && flags[i] <= flags[i + 1]) {
                int a = flags[i];
                flags[i] = flags[i + 1] + 1;
                sum += (flags[i] - a);
            } else {
                System.out.println(sum);
                return;
            }
        }

        System.out.println(sum);
    }
}
