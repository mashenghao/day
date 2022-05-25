package sort;


/**
 * 选择排序：
 * 和冒泡排序相似，区别在于选择排序是将每一个元素和它后面的元素进行比较和交换。
 */
public class selectSort {

    /**
     * 将数值中的每一个元素，与其后面的所有元素比较，选出最小值
     *
     * @param arr
     */
    public void sort(int[] arr) {

        for (int i = 0, len = arr.length; i < len; i++) {
            int minIndex = i;//记录最小值下标，和arr[i]交换位置
            for (int j = i + 1; j < len; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;//更新最小值元素下标
            }
            swap(arr, i, minIndex);//交换位置
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
        new selectSort().sort(new int[]{1, 2, 3, 88, 8, 10, 2, 9, 1, 2});
    }
}
