package basic.source.mh;

import java.util.Collection;
import java.util.Set;

/**
 * Map接口,存储键值对数据
 */
public interface Map<K, V> {


    /**
     * 存值，存在旧的数据，返回值是旧的Value，否则null
     *
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);//k ,v 使用泛型，是为了存入的时候，减少数据不一的风险

    //取值
    V get(Object key);//取值的时候采用Object，是使数据的获取条件不那么苛刻

    int size();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    Set<Entry<K, V>> entrySet();

    Set<K> keySet();

    Collection<V> values();

    V remove(Object key);

    void clear();


    /**
     * Map中存储的数据单元
     *
     * @param <K>
     * @param <V>
     */
    static interface Entry<K, V> {

        K getKey();

        V getValue();

        V setValue(V v);

        int hashCode();

        boolean equals(Object obj);
    }
}
