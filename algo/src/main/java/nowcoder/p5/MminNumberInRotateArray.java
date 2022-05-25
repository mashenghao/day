package nowcoder.p5;

/**
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * 输入一个非减排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
 * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
 */
public class MminNumberInRotateArray {

    public static int minNumberInRotateArray(int[] array) {
        if (array.length == 0)
            return 0;
        for (int i = array.length - 1; i >= 1; i--) {
            if (array[i] < array[i - 1])
                return array[i];
        }
        return array[0];
    }

    //{3,4,5,1,2}
    public static int minNumberInRotateArray2(int[] array) {

        int l = 0;
        int r = array.length - 1;
        int m = 0;
        while (r >= l) {
            m = (r + l) >> 1;
            if (array[m] > array[l]) {
                l = m;
            } else {
                r = m;
            }
            if (r - l == 1) {
                return array[r] > array[l] ? array[l] : array[r];
            }
        }

        return array[0];
    }


    public static void main(String[] args) {
        System.out.println(minNumberInRotateArray(new int[]{4, 3, 1, 2}));
    }
}
