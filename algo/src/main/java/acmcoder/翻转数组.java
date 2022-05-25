package acmcoder;

/**
 * 给定一个长度为n的整数数组a，元素均不相同，问数组是否存在这样一个片段，
 * 只将该片段翻转就可以使整个数组升序排列。其中数组片段[l,r]表示序列a[l], a[l+1], ..., a[r]。
 * 原始数组为
 * <p>
 * a[1], a[2], ..., a[l-2], a[l-1], a[l], a[l+1], ..., a[r-1], a[r], a[r+1], a[r+2], ..., a[n-1], a[n]，
 * <p>
 * 将片段[l,r]反序后的数组是
 * <p>
 * a[1], a[2], ..., a[l-2], a[l-1], a[r], a[r-1], ..., a[l+1], a[l], a[r+1], a[r+2], ..., a[n-1], a[n]。
 *
 * @author: mahao
 * @date: 2019/9/3
 */
public class 翻转数组 {


    public static void main(String[] args) {
        int[] arr = new int[]{4, 3, 2, 1};
        boolean flag = changArr(arr, arr.length);
        System.out.println(flag);
    }

    /**
     * 判断数组是否可以翻转成有序的。
     * 1 2 5 4 3 6 7 9
     *
     * @param arr
     * @return
     */
    public static boolean changArr(int[] arr, int n) {
        int i = 0;
        int l = i;
        while (i < n - 1 && arr[i] < arr[i + 1]) {
            i++;//定位到第一个不是升序排列的数
        }

        l = i;
        while (i < n - 1 && arr[i] > arr[i + 1]) {
            i++;//定位到最后一个不是降序排列的数
        }
        i++;

        //判断逆序数组两边是否，逆序第一个元素小于右半段升序数组，逆序的最后元素大于左半段元素
        //1.逆序第一个元素小于右半段升序数组
        if (i < n && arr[l] >= arr[i]) {
            return false;
        }

        //2.逆序的最后元素大于左半段元素
        if (i > 0 && l > 0 && arr[i - 1] <= arr[l - 1]) {
            return false;
        }
        while (i < n && arr[i] < arr[i + 1]) {
            i++;
        }
        return i == n;
    }
}
