package nowcoder;

import java.util.HashMap;

/**
 * 数组中数字超过一半的数
 */
public class MoreThanHalfNum_Solution {

    public int MoreThanHalfNum_Solution(int[] array) {

        if (array != null && array.length > 0) {
            if (array.length == 1) {
                return array[0];
            }
            int n = (array.length + 2) / 2;
            HashMap<Integer, Integer> map = new HashMap<>();
            Integer i = 0;
            for (int a : array) {
                i = map.get(a);
                if (i == null) {
                    map.put(a, 1);
                } else {
                    i++;
                    if (i >= n)
                        return a;
                    map.put(a, i);
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        MoreThanHalfNum_Solution m = new MoreThanHalfNum_Solution();
        int index = m.MoreThanHalfNum_Solution(new int[]{4, 2, 1, 4, 2, 4});
        System.out.println(index);
    }
}
