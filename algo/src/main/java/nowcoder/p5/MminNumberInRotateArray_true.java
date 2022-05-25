package nowcoder.p5;

/**
 *
 */
public class MminNumberInRotateArray_true {

    public static int minNumberInRotateArray(int[] array) {

        if (array.length == 0)
            return 0;
        int l = 0;
        int r = array.length - 1;
        int m = 0;
        while (l <= r) {

            m = (l + r) >> 1;
            if (array[r] < array[m]) {
                l = m;
            } else {
                r = m;
            }

            if (r - l == 1) {
                return array[r] > array[l] ? array[l] : array[r];
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int a = minNumberInRotateArray(new int[]{3,4,1});
        System.out.println(a);
    }
}
