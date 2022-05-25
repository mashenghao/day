package acmcoder;

import java.util.Scanner;

/**
 * 经过严密的计算，小赛买了一支股票，他知道从他买股票的那天开始，股票会有以下变化：
 * 第一天不变，以后涨一天，跌一天，涨两天，跌一天，涨三天，跌一天...依此类推。
 * <p>
 * 为方便计算，假设每次涨和跌皆为1，股票初始单价也为1，请计算买股票的第n天每股股票值多少钱？
 *
 * @author: mahao
 * @date: 2019/9/3
 */
public class 股神 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            System.out.println(cal(n));
        }
    }


    /*
    1
    2 1
    2 3 2
    3 4 5 4
    5 6 7 8 7
     */
    static int cal(int n) {
        int i = 0;//遇到了几次跌落
        int j = 3;//下次跌落需要的天数
        int k = 3;//当前第几天
        while (k <= n) {//6

            i++;
            k += j;
            j++;

        }
        return n - 2 * i;
    }

}
