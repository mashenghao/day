package interview.threadCreate;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 自动拆装箱
 */
public class AutoBoxing {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);//t
        System.out.println(e == f);//f
        System.out.println(c == (a + b));//t
        System.out.println(c.equals(a + b));//t
        System.out.println(g == (a + b));//t
        System.out.println(g.equals(a + b));//f
    }

    //自动装箱原理
    public void ClassAutoBoxing() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
    }

    //包装类型和基本类型用==比较，是拆箱，还是装箱
    @Test
    public void demo1() {
        int int100 = 100;
        Integer integer100 = 100;
        System.out.println(int100 == integer100);
    }

    //自动装箱操作
    @Test
    public void demo2() {
        Integer integer200 = 200;
        int int200 = 200;
        System.out.println(integer200.equals(int200));
    }


    // + -运算，是拆箱还是装箱了
    //==运算返回true，只有是数值一样，也就是int100 + integer100进行了拆箱
    @Test
    public void demo3() {
        int int100 = 200;
        Integer integer100 = 200;
        Long Long400 = 400l;
        System.out.println(Long400 == (int100 + integer100));
        System.out.println(Long400.equals(int100 + integer100));

    }
}
