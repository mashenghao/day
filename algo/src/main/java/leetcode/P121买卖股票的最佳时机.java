package leetcode;

/**
 * 给定一个数组 prices ，它的第i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * <p>
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * <p>
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author mahao
 * @date 2023/04/18
 */
public class P121买卖股票的最佳时机 {
    public static void main(String[] args) {
        int[] arr = new int[]{7, 1, 5, 3, 6, 4};
        int i = maxProfit2(arr);
        System.out.println(i);
    }

    /**
     * 1. 获利最多，需要最低点买入，最高点卖出。
     * 2. 也可能不是在最低点买入，但是在最高点卖出，这种收益也可能最大。
     * 3. 所以要将先前最大收益额保存出。 当遇到新的最低点时，在计算是否有新的收益额大于先前最大收益额。
     *
     * @param prices
     * @return
     */
    public static int maxProfit2(int[] prices) {
        int maxProfit = 0;
        int minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (minPrice > prices[i]) {
                minPrice = prices[i];
            } else {
                int money = prices[i] - minPrice;
                if (money > maxProfit) {
                    maxProfit = money;
                }
            }
        }
        return maxProfit;
    }

    public static int maxProfit(int[] prices) {
        int maxprofit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxprofit) {
                    maxprofit = profit;
                }
            }
        }
        return maxprofit;
    }

}
