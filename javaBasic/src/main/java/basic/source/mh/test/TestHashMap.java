package basic.source.mh.test;


import interview.SerializableDemo.User;
import org.junit.Test;
import basic.source.mh.HashMap;

import java.util.TreeSet;

/**
 * HashMap测试类
 */
public class TestHashMap {


    public static void main(String[] args) {
        System.out.println(123);
        HashMap<Object, Object> map = new HashMap<Object, Object>(0.25,15);
        System.out.println(map.put(1, 0));
        System.out.println(map.put(2, 2));
        System.out.println(map.put(1, 1));
        System.out.println(map.put(4, 4));
        System.out.println(map.put(17, 17));
        System.out.println(map.put(33, 33));
        System.out.println(map.put(1, 1111));
        System.out.println(map.put(49, 33));
        System.out.println(map.put(33, 3333333));
        System.out.println(map.put(null, 3333333));

        System.out.println("get测试");
        System.out.println(map.get(null));
        System.out.println("remove测试");
        System.out.println(map.remove(1));
        System.out.println(map.remove(49));
        System.out.println(map);

        TreeSet<User> treeSet = new TreeSet<>();
    }


    /**
     * 判断高位是不是 1
     */
    @Test
    public void testHasHeigh1(){
        int n = 16;
    for (int i =0;i<33;i++){
        System.out.println(i+ "  ---  "+ ((n)&i));
    }

    }
}
