package CVTE;

/**
 * 求数组中最大的差值，要求max = arr[n+i]-arr[n] ,i>0
 *
 * @author: mahao
 * @date: 2019/9/17
 */
public class Problem1 {


    public static void main(String[] args) {
        int[] arr = {
                7, 1, 5, 3, 6, 4};
        int maxNum = getMaxNum(arr);
        System.out.println(maxNum);
    }

    public static int getMaxNum(int[] arr) {
        int max = arr[0];
        int num = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - num > max) {
                max = arr[i] - num;
            }
            if (arr[i] < num) {
                num = arr[i];
            }
        }
        return max;
    }
}
