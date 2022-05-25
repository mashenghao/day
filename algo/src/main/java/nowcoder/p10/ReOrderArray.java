package nowcoder.p10;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使
 * 得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变
 */
public class ReOrderArray {

    /**
     * 尚未找出更好的方法，时间复杂度为o(n*n)
     * <p>
     * 碰到偶数，插入到末尾，直到最后一位
     *
     * @param array
     */
    public void reOrderArray(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int t = 0;
        int l = 0;

        int j = 0;
        for (int i = 0; i < array.length - l; i++) {
            if ((array[i] & 1) == 0) {
                t = array[i];
                for (j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[j] = t;
                l++;
                i--;
            }

        }
        for (int a : array) {
            System.out.print(a + " ");
        }
    }

    public static void main(String[] args) {
        ReOrderArray r = new ReOrderArray();
        r.reOrderArray(new int[]{1, 2, 3, 4, 5, 6});
    }
}
