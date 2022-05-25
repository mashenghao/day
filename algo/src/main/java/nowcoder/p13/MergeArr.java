package nowcoder.p13;

/**
 * 合并两个有序非递减数组 剑指offer P49页:
 * 条件：
 * A数组中的容量可以容纳A B两数组之和
 * <p>
 * 思路：
 * 将B数组中数据拷贝到A数组，需要循环，倘若从前往后循环，对于A数组中的数据，
 * 则需要移动，最坏情况需要o（n^2）,所以考虑从后往前，则可以一遍结束，复杂度也是o（n+m）
 */
public class MergeArr {

    public void merge(int[] arr1, int n, int[] arr2) {
        //顺序很重要，如果arr2等于空，则不需要合并，arr1不需要改变
        if (arr2 == null) {
            return;
        }
        //如果arr1等于空，arr2不等于null，则将arr2赋值给arr1，不需要其他判断
        if (arr1 == null) {
            arr1 = arr2;
            return;
        }

        int p1 = n - 1;//arr1数组末尾元素指针
        int p2 = arr2.length - 1;//arr2数组末尾元素指针
        int p = p1 + p2 + 1;//arr1 和 arr2 合并后末尾元素指针
        while (p1 >= 0 && p2 >= 0) {
            if (arr1[p1] >= arr2[p2]) {
                arr1[p] = arr1[p1--];
            } else {
                arr1[p] = arr2[p2--];
            }
            p--;
        }
        while (p2 >= 0) {
            arr1[p--] = arr2[p2--];
        }
        System.out.print("[");
        for (int a : arr1) {
            System.out.print(a + ",");
        }
        System.out.print("]");
    }

    public static void main(String[] args) {
        MergeArr m = new MergeArr();
        int[] arr1 = new int[15];
        arr1[0] = 1;
        arr1[1] = 1;
        arr1[2] = 3;
        arr1[3] = 5;
        arr1[4] = 6;
        arr1[5] = 9;
        int[] arr2 = new int[]{3, 5, 7, 9, 10};
        m.merge(arr1, 6, arr2);
    }
}
