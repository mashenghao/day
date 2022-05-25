package sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 希尔排序：
 */
public class shellSort {

    static int[] arr = {8, 9, 1, 7, 2, 3, 4, 6, 0};


    //基于交换法的希尔排序
    public void sort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {//需要进行几次步数排序，每次步数是gap
            for (int i = gap; i < arr.length; i++) {//在跨步数之间，进行增量排序
                for (int j = i - gap; j >= 0 && arr[j] > arr[j + gap]; j -= gap)
                    swap(arr, j, j + gap);// 如果当前元素大于加上步长后的那个元素，说明交换
            }
        }
    }

    //基于移位法的希尔排序
    public void sort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {//需要进行几次步数排序，每次步数是gap
            for (int i = gap; i < arr.length; i++) {//在跨步数之间，进行增量排序
                int key = arr[i], j;
                for (j = i - gap; j >= 0 && arr[j] > key; j -= gap)
                    arr[j + gap] = arr[j];
                arr[j + gap] = key;
            }
        }
    }

    @Test
    public void testSort() {
        // 创建要给80000个的随机的数组
        for (int k = 0; k <= 10; k++) {
            int n = k;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = (int) (Math.random() * 100); // 生成一个[0, 8000000) 数
            }
            new shellSort().sort2(arr);

            System.out.println(Arrays.toString(arr));
        }
    }

    //9个数进行排序
    //希尔排序的过程，用交换法
    public static void main(String[] bs) {
        System.out.println(Arrays.toString(arr));
        //用到的是插入排序，要尽到第二个元素的位置,所以i的值，就是每组数的第二个元素的下标，最小的那个
        //进行第一轮排序 9/2 = 4
        for (int i = 4; i < arr.length; i++) {
            for (int j = i - 4; j >= 0 && arr[j] > arr[j + 4]; j -= 4) {
                swap(arr, j, j + 4);
            }
        }
        System.out.println(Arrays.toString(arr));

        //进行第二轮排序 4/2 = 2,i的值变成了第一个分组中的第二个元素，这一次采用移位法
        for (int i = 2; i < arr.length; i++) {
            int key = arr[i], j;
            for (j = i - 2; j >= 0 && arr[j] > key; j -= 2)//用前一个元素，和后一个比较，如果前一个大，后移
                arr[j + 2] = arr[j];
            arr[j + 2] = key;//合适位置插入
        }
        System.out.println(Arrays.toString(arr));


        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j;
            for (j = i - 1; j >= 0 && arr[j] > key; j--)
                arr[j + 1] = arr[j];
            arr[j + 1] = key;
        }
        System.out.println(Arrays.toString(arr));
    }


    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
