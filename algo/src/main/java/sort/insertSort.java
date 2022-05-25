package sort;


import java.util.Arrays;

/**
 * 插入排序：
 * 将元素逐个的插入到有序数组的合适位置，
 * 有序数组，默认一开始将首元素作为有序数组，然后进入排序。
 */
public class insertSort {

    //移位法
    public void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j;//要插入的元素
            for (j = i - 1; j >= 0 && arr[j] > key; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = key;

            /*j = i-1;
            while(j>=0&&arr[j]>key){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;*/
        }
        printArr(arr);
    }

    //交换法
    public void sort2(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }

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

    public static void main(String[] args) {
        //int[] arr = { 8, 9, 1, 7, 2, 3, 5, 4, 6, 0 };

        // 创建要给80000个的随机的数组
        for (int k = 0; k <= 10; k++) {
            int n = k;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = (int) (Math.random() * 100); // 生成一个[0, 8000000) 数
            }
            new insertSort().sort2(arr);

            System.out.println(Arrays.toString(arr));
        }


    }


}
