package sort;

import org.junit.Test;

/**
 * 冒泡排序算法：
 * <p>
 * 运行过程:
 * *进行n-1次排序
 * *每次排序从0~n-1-i（i是编号）下标数组中，相邻的数，如果a[j]>a[j+1],则
 * 交换位置，每次排序，都能出现一个最大的数组。
 */
public class BubbleSort {

    int[] arr = {5, 1, 3, 5, 9, 2, 5, 6, 8, 7};


    //经典的冒泡  排序算法
    @Test
    public void sort1() {
        //需要循环几次，每次循环出来一个最值，当n-1个数，排序完，最后一个则不要
        for (int i = 0, len = arr.length; i < len - 1; i++)
            for (int j = 0; j < len - 1 - i; j++)//将前一个和后一个进行比较
                if (arr[j] > arr[j + 1])
                    swap(arr, j, j + 1);

        printArr(arr);
    }

    //边界值进行优化
    @Test
    public void sort2() {
        for (int i = arr.length - 1; i > 0; i--)
            for (int j = 0; j < i; j++)
                if (arr[j] > arr[j + 1])
                    swap(arr, j, j + 1);
    }

    /**
     * 第一次优化： mark
     * 加入标记，判断是否已经排好序了
     * mark；
     */
    @Test
    public void sort3() {
        for (int i = arr.length - 1; i > 0; i--) {
            boolean mark = true; //数组是否有序标记
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    mark = false;
                }
            }
            if (mark) return;//数组是有序的，则结束其他排序
        }
    }

    /**
     * 第二次优化： border
     * 记录上次排序最后一次交换的位置，作为下一次排序，元素比较交换的结束位置。
     * 如果上一次排序的最后交换位置，是最后一个元素，则就是冒泡排序算法，
     * 如果是最后位置，是前几个，则证明前几个后的元素是排好序的，无需在排，可以跳过。
     */
    @Test
    public void sort4() {
        for (int i = arr.length - 1; i > 0; i--) {
            int border = 0;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    border = j + 1;//记录最后一次的交换位置
                }
            }
            i = border;//调到最后一次的交换位置
        }

        //两者实现都可以，主要是区分边界问题
        for (int i = 0, len = arr.length; i < len - 1; i++) {
            int border = 0;
            for (int j = 0; j < len - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    border = j + 1;//只需要记住，border记录的是最后一次的数值交换
                }
            }
            i = len - border - 1; //i的值，是数组尾部有多少排好的元素
        }
    }

    /**
     * 鸡尾酒排序：
     * 实现一次遍历出现一个最大值，一个最小值
     * <p>
     * （可以使用加入标记进行优化）
     */
    @Test
    public void sort5() {
        int L = 0, R = arr.length - 1;
        while (L < R) {//L=R时，表示只剩一个元素没有排序，这一个元素也是有序的
            for (int i = 0; i < R; i++) if (arr[i] > arr[i + 1]) swap(arr, i, i + 1);//出现最大值
            R--; //记录末尾尚未排序的下标
            for (int i = R; i > L; i--) if (arr[i] < arr[i - 1]) swap(arr, i, i - 1); //出现最小值
            L++;//记录开头尚未排序元素的下标
        }
        printArr(arr);
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void printArr(int[] arr) {
        System.out.print("[ ");
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("]");
    }
}
