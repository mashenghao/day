package cn.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * @author: mahao
 * @date: 2020/2/26
 */
public class QuickSort {

    public static void main(String[] args) {
        Integer[] arr = new Integer[20];
        Random random = new Random(1);

        for (int i = 0; i < 20; i++) {
            arr[i] = random.nextInt();
        }
        quickSort(0, arr.length - 1, arr);
        System.out.println(Arrays.asList(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.asList(arr));
    }

    public static <T extends Comparable> void quickSort(int l, int r, T[] arr) {

        //递归结束条件
        if (l >= r) {
            return;
        }
        T key = arr[l];
        int i = l, j = r;
        while (i < j) {
            while (j > i && arr[j].compareTo(key) >= 0) {
                j--;
            }
            while (i < j && arr[i].compareTo(key) <= 0) {
                i++;
            }
            if (i < j) {
                T tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }

        //交换基数
        arr[l] = arr[i];
        arr[i] = key;

        quickSort(l, i - 1, arr);
        quickSort(i + 1, r, arr);

    }
}
