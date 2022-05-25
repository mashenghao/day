package sort;

/**
 * 快速排序：
 * 思想就是使元素归位
 */
public class Quicksort {

    /**
     * 快速排序经典算法：（交换）
     * 从左右两边向中间推进的时候，遇到不符合的数就两边交换值。
     * <p>
     * 左右设置两个哨兵，右哨兵移动，找到小于key的元素，然后左哨兵移动，
     * 最后确定哨兵的位置。
     */
    public void sort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        quickSort(0, arr.length - 1, arr);
    }

    //[5,1,1,2,0,0]
    public void quickSort(int begin, int end, int[] arr) {

        if (begin >= end)
            return;

        int l = begin;
        int r = end;
        int key = arr[begin];
        while (l < r) {//将元素分类存放，以key大小比较
            //顺序很重要，从右面开始找
            while (l < r && arr[r] >= key)
                r--;
            while (l < r && arr[l] <= key)
                l++;
            if (l < r) {//表示存在不相符的元素
                int t = arr[l];
                arr[l] = arr[r];
                arr[r] = t;
            }
        }
        //交换基数,让key归位
        arr[begin] = arr[l];
        arr[l] = key;

        quickSort(begin, l - 1, arr);
        quickSort(l + 1, end, arr);
    }

    /**
     * 快速排序之填坑
     * <p>
     * 从右边向中间推进的时候，遇到小于基数的数就赋给左边（一开始是基数的位置），
     * 右边保留原先的值等之后被左边的值填上。
     */
    public void sort2(int arr[]) {

        if (arr == null || arr.length <= 1)
            return;
        quickProcess(arr, 0, arr.length - 1);
    }

    private void quickProcess(int[] arr, int L, int R) {

    }


}
