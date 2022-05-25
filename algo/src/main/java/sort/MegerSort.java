package sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MegerSort {

    @Test
    public void testSort() {
        // 创建要给80000个的随机的数组
        for (int k = 0; k <= 10; k++) {
            int n = k;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = (int) (Math.random() * 100); // 生成一个[0, 8000000) 数
            }
            System.out.println(Arrays.toString(arr));
            int[] temp = new int[k];
            new MegerSort().sort(arr, 0, arr.length - 1, temp);

            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 归并排序，先将数据进行拆分，然后进行两个有序数组的合并，归并思想
     *
     * @param arr   数组
     * @param left  数组起始索引
     * @param right 数组结束索引
     * @param temp  临时数组
     */
    public void sort(int[] arr, int left, int right, int[] temp) {
        //先进行查分，查分成左右只有一个元素的数组
        if (left < right) {
            int mid = (left + right) / 2;
            //继续进行拆分,拆分左边的
            sort(arr, left, mid, temp);
            //查分右边
            sort(arr, mid + 1, right, temp);

            //当两边只有一个元素了，进行合并
            meger(arr, left, mid, right, temp);
        }

    }

    /**
     * 合并两个有序数组，arr[left~mid] 和 arr[mid+1~right]
     *
     * @param arr
     * @param left
     * @param mid
     * @param right
     * @param temp
     */
    public void meger(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        int t = 0;//临时数组存储合并后的元素
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }

        //剩余元素
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        while (j <= right) {
            temp[t++] = arr[j++];
        }

        //拷贝进原数组
        t = 0;
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }
}
