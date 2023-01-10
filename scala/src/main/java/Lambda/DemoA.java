package Lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: mahao
 * @date: 2020/10/27
 */
public class DemoA {

    public static void main(String[] args) {
        DemoA demoA = new DemoA();
        demoA.sort(Arrays.asList(1, 2, 4, 5, 6, 3, 2));
    }


    public void sort(List<Integer> list) {
        Collections.sort(list, (o1, o2) -> {
            System.out.println("lambda函数");
            return o1.compareTo(o2);
        });
        System.out.println(list);
    }
}
