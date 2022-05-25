package interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Arrays使用
 *
 * @author: mahao
 * @date: 2019/9/1
 */
public class HArrays {

    /**
     * asList()返回的list对象，能将集合转换为list对象；
     * <p>
     * 注意
     * 把数组转为集合时，不能使用其修改的集合的方法，如add/remove/clear方法，
     * 会抛出unsupportedOperationExceptioon异常。
     * <p>
     * 原因：
     * asList方法返回的对象是内部类创建的对象，没有实现集合的修改方法，
     * Arrays.asList体现的是适配器模式，只是转换接口，后台数据依然是数组。
     */
    @Test
    public void testAsList() {
        List<String> list = Arrays.asList("1", "2", "3");
        System.out.println(list);
        Iterator<String> it = list.iterator();
        list.add("4");//不支持操作异常
        while (it.hasNext()) {
            String str = it.next();
            System.out.println(str);
            it.remove();
        }
    }

    /**
     * 注意事项：
     */
    @Test
    public void testAsList2() {
        /*
        1.asList传入的必须是对象数组，基本类型的数组会被当成一个对象
        原因：
        Arrays.asList()是泛型方法，传入的对象必须是对象数组。基本类型数组，会被当成只是一个对象
        当传入一个原生数据类型数组时，Arrays.asList() 的真正得到的参数就不是数组中的元素，而是数组对象本身！
         */
        int[] arr = {1, 2, 3};
        List list = Arrays.asList(arr);
        System.out.println(list.get(0));

        /*
        2.使用修改操作，会抛出异常；
        案例参见testAsList；
         */


        /*
        3.Arrays.asList() 方法返回的并不是 java.util.ArrayList ，
        而是 java.util.Arrays 的一个内部类,
        这个内部类并没有实现集合的修改方法或者说并没有重写这些方法。
         */
        List myList = Arrays.asList(1, 2, 3);//自动装箱
        System.out.println(myList.getClass());//class java.util.Arrays$ArrayList
    }


    /*************************    如何正确的将数组转换为ArrayList?   *************************/

    @Test
    public void testToList() {
        Integer[] arr = {1, 2, 3, 4, 5, 6};

        //1.最简便的方法，通过Arraylist的构造方法，就是调用toArray方法，去数组拷贝
        List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(arr));

        //2.自定义实现
        List<Integer> list2 = toList(arr);
    }

    public static <T> List<T> toList(final T... ts) {
        ArrayList<T> list = new ArrayList<>(ts.length);
        for (T t : ts) {
            list.add(t);
        }
        return list;
    }

    @Test
    public void testToList2() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for (String s : list) {
            if ("2".equals(s)) {
                list.remove(s);
            }
            System.out.println(s);

        }
        System.out.println(list);
    }
}
