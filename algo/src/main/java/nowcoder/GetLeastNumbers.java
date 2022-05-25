package nowcoder;


import java.util.ArrayList;
import java.util.Comparator;

/**
 * 输入n个整数，找出其中最小的K个数。
 * 例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
 */
public class GetLeastNumbers {
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {


        int max = Integer.MIN_VALUE, n = input.length;
        ArrayList<Integer> list = new ArrayList(k);
        if (input.length < k || k == 0) {
            return list;
        }
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        list.add(input[0]);
        for (int i = 1; i < n; i++) {
            if (list.size() < k) {
                list.add(input[i]);
            } else if (input[i] < list.get(k - 1)) {
                list.set(k - 1, input[i]);
            }

            list.sort(comparator);
        }
        return list;
    }

    public static void main(String[] args) {
        GetLeastNumbers g = new GetLeastNumbers();
        int[] ints = new int[]{4, 5, 1, 6, 2, 7, 3, 8};
        ArrayList<Integer> list = g.GetLeastNumbers_Solution(ints, 4);
        System.out.println(list);

    }
}
