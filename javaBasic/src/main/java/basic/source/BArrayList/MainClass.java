package basic.source.BArrayList;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * List体系源码学习
 */
public class MainClass {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(null);
        list.remove(1);
        list.remove(new Integer(1));
        list.add(1, 1);
        list.set(1, 1);

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.print(next + " ");
        }
        iterator.remove();
    }

    /**
     * 将指定源数组中的数组从指定位置复制到目标数组的指定位置。
     * System.arraycopy(src,srcPos,dest,destPos,length);
     */
    @Test
    public void demo1() {
        Object[] elementData = new Object[5];
        elementData[0] = 'a';
        elementData[1] = 'b';
        elementData[2] = 'c';
        elementData[3] = 'd';
        elementData[4] = 'e';

        Object[] des = new Object[10];
        des[0] = 0;
        des[1] = 1;
        des[2] = 2;
        System.arraycopy(elementData, 0, des, 3,
                2);
        System.out.println(Arrays.asList(elementData));
        System.out.println(Arrays.asList(des));
    }


    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    @Test
    public void demo2(){
        System.out.println(EMPTY_ELEMENTDATA==DEFAULTCAPACITY_EMPTY_ELEMENTDATA);
    }

    @Test//fast-fail
    public void demo3(){
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");

        for (String temp : a) {
            System.out.println(temp);
            if("2".equals(temp)){
                a.remove(temp);
            }
        }
    }
}
