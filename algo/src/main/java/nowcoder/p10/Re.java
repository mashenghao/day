package nowcoder.p10;

/**
 * 剑指offer上考察的是扩展性，而不是这个什么鬼
 * <p>
 * 原题：
 * 调整数组奇数和偶数位置，是偶数在前，奇数在后
 * <p>
 * 再问：
 * 如何负数在前，正数在后
 * <p>
 * .....  所以这一题考察的是扩展性，所以可以解耦比较方式，让条件判定和运算解耦出来
 */
public class Re {


    /**
     * 解答问题一 ：
     * 调整数组奇数和偶数位置，是偶数在前，奇数在后
     * <p>
     * 思路声明两个指针，一个在前，一个在后，前面遇到奇数停下，后面遇到偶数停下，
     * 然后交换位置
     *
     * @param arr
     */
    public void question1(int[] arr) {

        if (arr == null || arr.length == 0)
            return;

        int l = 0, r = arr.length - 1;
        while (l < r) { //只有右边大于左边，否则就循环完毕了

            while (l < r && (arr[l] & 1) != 1) {//向后移动l，直到指向奇数
                l++;
            }

            while (l < r && (arr[r] & 1) == 1) {//向前移动，直到指到偶数
                r--;
            }

            if (l < r) {
                swap(arr, l, r);
                //   l++;r--;
            }

        }
    }

    /**
     * 满足可扩展性，将判断条件独立出来
     *
     * @param arr
     */
    public void question2(int[] arr) {

        if (arr == null || arr.length == 0)
            return;

        int l = 0, r = arr.length - 1;

        while (l < r) {
            while (l < r && isEven(arr[l])) {
                l++;
            }
            while (l < r && !isEven(arr[r])) {
                r--;
            }
            if (l < r) {
                swap(arr, l, r);
            }
        }
    }

    //奇数在前，偶数在后
    public boolean isEven(int i) {
        return (i & 1) == 1;
    }

    private void swap(int[] arr, int l, int r) {
        arr[l] = arr[l] ^ arr[r];
        arr[r] = arr[l] ^ arr[r];
        arr[l] = arr[l] ^ arr[r];
    }


    public static void main(String[] args) {
        Re r = new Re();
        int arr[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        r.question2(arr);
        for (int a : arr) {
            System.out.print(a + " ");
        }

    }
}
