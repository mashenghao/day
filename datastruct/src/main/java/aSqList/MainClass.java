package aSqList;

import java.util.Arrays;
/**
 * 线性表的用法
 *
 * @author  mahao
 * @date:  2019年3月18日 下午9:39:33
 */
public class MainClass {

	public static void main(String[] args) {
		int[] arr = { 1, 3 };
		sortJO(arr);
		System.out.println(Arrays.toString(arr));
		int[] arr1 = {1,2,4,6,8,9,45,258};
		int[] arr2 = {1,3,5,45,89,782};
		mergicList(arr1, arr2);
	}

	public static void sortJO(int[] arr) {// 奇数在前，偶数在后
		for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
			while (arr[i] % 2 != 0 && i < j) {
				i++;
			}
			while (arr[j] % 2 == 0 && i < j) {
				j--;
			}
			if (i < j) {
				int t = arr[i];
				arr[i] = arr[j];
				arr[j] = t;
			}
		}
	}

	/* 两个非递减有序表A和B，并把它们合并成一个非递减有序表C */
	public static void mergicList(int[] arr1, int[] arr2) {
		int[] arr3 = new int[arr1.length + arr2.length];
		int k = 0;
		int i = 0, j = 0;
		for (; i < arr1.length && j < arr2.length;) {
			if (arr1[i] <= arr2[j]) {
				arr3[k] = arr1[i];
				k++;
				i++;
			} else {
				arr3[k] = arr2[j];
				k++;
				j++;
			}
		}
		while (i < arr1.length) {
			arr3[k++] = arr1[i++];
		}
		while (j < arr2.length) {
			arr3[k++] = arr2[j++];
		}
		System.out.println(Arrays.toString(arr3));
	}
}
