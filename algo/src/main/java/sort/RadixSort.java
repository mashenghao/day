package sort;

import java.util.Arrays;

/**
 * 基数排序：
 */
public class RadixSort {

    /**
     * 基数排序是桶排序变换而来，他将元素的每个位数的数字，进行桶归位，
     * 直达最高位数归位结束，之后顺序输出就是结果；
     *
     * @param arr
     */
    public static void sort(int[] arr) {

        //定义位桶，和位桶的计数器
        int[][] bucket = new int[10][arr.length];//记录存在桶中的数据
        int[] bucketCount = new int[10];//记录每个位桶中的有效数字个数
        int maxEle = 3;//循环次数，最大值的位数
        int div = 1;//求个位，十位的除数
        for (int i = 0; i < maxEle; i++, div *= 10) {//根据最大值的位数，决定循环次数
            //1. 放置元素
            for (int j = 0; j < arr.length; j++) {//将每个元素按照位数入桶
                int digitOfElement = (arr[j] / div) % 10;
                //arr[j]根据余数放置到相同位桶中
                bucket[digitOfElement][bucketCount[digitOfElement]] = arr[j];
                bucketCount[digitOfElement]++;//计数器增加
            }

            //2.顺序取出元素，放入数组
            int index = 0;
            for (int k = 0; k < bucket.length; k++) {
                if (bucketCount[k] > 0) {//桶中存有元素
                    for (int l = 0; l < bucketCount[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                    bucketCount[k] = 0;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void main(String[] args) {
        int arr[] = {53, 3, 542, 748, 14, 214};
        sort(arr);
    }
}
