package offer;

import java.util.Map;

/**
 * @author mahao
 * @date 2022/08/12
 */
public class P1整数除法 {
    public static void main(String[] args) {
        int divide = new Solution().divide(10, 2);
        System.out.println(divide);
    }

    static class Solution {
        public int divide(int a, int b) {
            boolean zs = (a < 0 || b > 0) && (a > 0 || b < 0);
            a = Math.abs(a);
            b = Math.abs(b);
            int res = 0;
            while (a >= b) {
                a -= b;
                res++;
            }
            return zs ? res : -res;
        }
    }
}

