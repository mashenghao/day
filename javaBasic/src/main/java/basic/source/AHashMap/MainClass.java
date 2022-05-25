package basic.source.AHashMap;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * HashMap实现原理
 */
public class MainClass {

    public static void main(String[] args) {
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, "1");
        map.put(2, "0");
        map.put(3, "0");
        map.put(17, "2");
        map.put(33, "3");
        int n = 33;
        map.put(n += 16, "4");
        map.put(n += 16, "5");
        map.put(n += 16, "6");
        map.put(n += 16, "7");
        map.put(n += 16, "8");
        map.put(n += 13, "9");
        map.put(n += 16, "10");
        map.put(n += 16, "10");
        Object o = map.get(33);
        map.entrySet().add(new HashMap.SimpleEntry<Integer, Object>(5, "5555"));
        System.out.println(o);

        Map abstractMap = new AbstractMap() {
            @Override
            public Set<Entry> entrySet() {
                return null;
            }
        };
    }

    @Test
    public void testHash() {
        Object key = 1;
        int h;
        int s;
        int result = 0;
        if (key == null) {
            result = 0;
        } else {
            h = key.hashCode();
            s = h >>> 16;
            result = h ^ s;
        }
        System.out.println(result);
    }
}
