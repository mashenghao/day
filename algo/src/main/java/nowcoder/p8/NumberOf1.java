package nowcoder.p8;

/**
 * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
 * <p>
 * 思路：
 * 正数：直接%2取余，如果是1 ，则记录
 * 负数：
 * 先按照正数，计算二进制数
 * 对这一位进行--取反码
 * 然后取--取补码  ---> 判断是否有位移
 */
public class NumberOf1 {


    /**
     * 因为是位运算，所以使用位运算符是最简便的思路
     * <p>
     * 假设 n 是直接进行位运算 ，现在n = 1101 (13) 如果与1进行与预算，得出0001，就是余数,
     * 然后只需要n 右移1位，就是求下一位的余数
     *
     * @param n
     * @return
     */
    public int NumberOf2(int n) {
        int count = 0;
        while (n != 0) {

            if ((n & 1) != 0) {
                count++;
            }
            n = n >>> 1;
        }
        return count;
    }

    /**
     * 无法满足负数，负数左补的数字是1，会陷入死循环,
     * 所以可以利用无符号右移解决，上面已经更改，
     * 也可以使用：
     * 让相与的数，完成左移，然后进行相与运算
     *
     * @param n
     * @return
     */
    public int NumberOf3(int n) {
        int count = 0;//1010
        int flag = 1; //一开始等于1，计算第一位
        while (flag != 0) {//循环结束条件是flag左移了超过32位，则有效位全是0
            if ((n & flag) != 0) {
                //如果计算的这一位是1
                count++;
            }
            //flag左移一位，去计算n的下一位
            flag = flag << 1;
        }
        return count;
    }

    /**
     * 最优解：通过对n-1操作，实现二进制下借位，将高位1变为0，
     * 直到高位全变为0，则可以推断多少个1
     *
     * @param n
     * @return
     */
    public int NumberOf_true(int n) {
        int count = 0;//1010
        while (n != 0) {
            count ++ ;
            //n-1是借位操作，&n是将非有效数字1 清除掉
            /*
               比如 100101
               第一次 减一 变为100100 & 100101 = 100100
               第二次 减一 变为100011 & 100100 = 100000
               第三次 减一 变为011111 & 100000 = 000000
               所以一共三次
             */
            n = (n-1) & n ;
        }
        return count ;
    }

    public int NumberOf1(int n) {
        if (n == -2147483648) {
            return 1;
        }
        int count = 0;
        if (n >= 0) {//正数
            int mod;
            while (n > 0) {
                mod = n % 2;
                System.out.print(mod);
                if (mod == 1) {
                    count++;
                }
                n = n / 2;
            }
        } else {//负数
            n = -n;
            int modCount = 0;
            int mod = 0;
            int lastresult = 1;
            while (n > 0) {

                mod = n % 2;
                if (mod == 1) {
                    mod = 0;
                } else {
                    mod = 1;
                }
                mod += lastresult;
                if (mod > 1) {
                    mod = 0;
                    lastresult = 1;
                } else {
                    lastresult = 0;
                }

                System.out.print(mod);
                if (mod == 1) {
                    count++;
                }
                modCount++;
                n = n / 2;
            }
            count += (32 - modCount);

        }
        return count;
    }

    public static void main(String[] args) {
        NumberOf1 numberOf1 = new NumberOf1();
        int n = 10;
        int result = numberOf1.NumberOf1(n);

        System.out.println();
        System.out.println(result + "--" + numberOf1.NumberOf3(n));
    }
}
