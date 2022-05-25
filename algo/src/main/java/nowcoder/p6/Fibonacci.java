package nowcoder.p6;

/**
 * @program: algorithm
 * @Date: 2019/6/10 17:06
 * @Author: mahao
 * @Description:
 */
/*
大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
    n<=39
 */

public class Fibonacci {

    public int fibonacci(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 1;
        } else
            return fibonacci(n-1) + fibonacci(n - 2);
    }

    /**
     * 使用循环方式，用a 记录f(n-1)的结果，b则是f(n)的数据，当没循环一次，
     * b = b + a; b就是n项的结果，a则也要跟新为b的值,则a= b-a; (b=b+a)，
     * 注意a b 的初始值，a = 1 ，b = 0 ；
     * @param n
     * @return
     */
    public int fibonacci2(int n) {
        int a = 1, b = 0;
        while (n-- > 0) {
            b = a + b;
            a = b - a;
        }
        return b;
    }

    public static void main(String[] args) {
        Fibonacci f = new Fibonacci();
        int n = 20 ;
        System.out.println(f.fibonacci(n));
        System.out.println(f.fibonacci2(n));
    }
}
