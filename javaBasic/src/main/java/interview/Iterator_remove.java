package interview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2019/9/28
 */
public class Iterator_remove {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Integer i : list) {
            if (i ==  3) {
                list.remove((Integer)3);
            }
        }
        System.out.println(list.size());
    }
}
