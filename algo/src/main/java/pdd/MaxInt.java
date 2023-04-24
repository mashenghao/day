package pdd;

/**
 * 给出一组非负整数，重新排列他们的
 * 顺序把他们组成一个最大的整数
 * <p>
 * 给出[1,20,23,4,8]，返回组合最大的整数应为8 4 23 20 1
 * <p>
 * 给出[1,201,20,9,8]，返回组合最大的整数应为9 8 20 201 1
 * <p>
 * 给出[2,201,20,9,8]，返回组合最大的整数应为
 * 9 8 2 20 201
 *
 * <p>
 * 给出[1，203，20,9,8]，返回组合最大的整数应为9 8 203 20 1
 *
 * @author mahao
 * @date 2023/04/12
 */
public class MaxInt {
    /**
     * 20 202
     * <p>
     * 20202
     * 20220
     *
     * @param args
     */
    public static void main(String[] args) {
        int arr[] = {3, 30, 34, 5, 9};
        //9 5 34 3 30
        // 9 8 2 20 202 201 2001
        //先排序
        largestNumber(arr);
    }

    public static String largestNumber(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = arr.length - 1; j > arr.length - 1 - i; j--) {
                if (isMaxA(arr[j], arr[j - 1])) {
                    int t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(i);
        }
        return sb.toString();
    }


    public static boolean isMaxA(int a, int b) {
        char[] as = String.valueOf(a).toCharArray();
        char[] bs = String.valueOf(b).toCharArray();
        int len = as.length > bs.length ? bs.length : as.length;
        for (int i = 0; i < len; i++) {
            if (as[i] > bs[i]) {
                return true;
            } else if (bs[i] > as[i]) {
                return false;
            } else {

            }
        }
        //3
        //34
        //越短的越在前面。
        boolean isALengthMin = as.length < bs.length;
        if (isALengthMin) {
            if (bs[as.length] > as[0]) {
                return false;
            }
        }
        return isALengthMin;

    }
}
