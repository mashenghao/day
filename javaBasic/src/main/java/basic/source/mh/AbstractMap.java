package basic.source.mh;


import java.util.*;

/**
 * 抽象Map实现类
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    protected transient Set<K> keySet;  //做缓存
    protected transient Collection<V> values;

    @Override
    public V put(K key, V value) {

        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        Iterator<Entry<K, V>> it = entrySet().iterator();
        if (key == null) {
            while (it.hasNext()) {
                Entry<K, V> e = it.next();
                if (e.getKey() == null)
                    return e.getValue();

            }
        } else {
            while (it.hasNext()) {
                Entry<K, V> e = it.next();
                if (key.equals(e.getKey()))
                    return e.getValue();

            }
        }
        return null;
    }

    @Override
    public int size() {
        return entrySet().size();
    }

    @Override
    public boolean containsKey(Object key) {
        Iterator<Entry<K, V>> it = entrySet().iterator();
        if (key == null) {
            while (it.hasNext()) {
                Entry<K, V> e = it.next();
                if (e.getKey() == null)
                    return true;
            }
        } else {
            while (it.hasNext()) {
                Entry<K, V> e = it.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator<Entry<K, V>> i = entrySet().iterator();
        if (value == null) {
            while (i.hasNext()) {
                Entry<K, V> e = i.next();
                if (e.getValue() == null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K, V> e = i.next();
                if (value.equals(e.getValue()))
                    return true;
            }
        }
        return false;
    }

    @Override
    public abstract Set<Entry<K, V>> entrySet();


    /**
     * 自定义实现Set集合，作为返回参数
     *
     * @return
     */
    public Set<K> keySet() {

        Set<K> ks = keySet;//将成员变量赋值给局部变量，将数据拷贝到方法栈中，变量存取快

        if (ks == null) {
            ks = new AbstractSet<K>() {

                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.size() == 0;
                }

                @Override
                public boolean contains(Object o) {
                    return AbstractMap.this.containsKey(o);
                }

                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }

                @Override
                public Iterator<K> iterator() {
                    return new KeysIterator();
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
            };
            keySet = ks;
        }
        return ks;

    }

    @Override
    public Collection<V> values() {
        Collection<V> vals = values;
        if (vals == null) {
            vals = new AbstractCollection<V>() {
                @Override
                public Iterator<V> iterator() {
                    return new ValuesIterator();
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }

                @Override
                public boolean isEmpty() {
                    return super.isEmpty();
                }

                @Override
                public boolean contains(Object o) {
                    return AbstractMap.this.containsValue(o);
                }

                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }
            };
            values = vals;
        }
        return vals;
    }

    /**
     * 删除元素
     *
     * @param key
     * @return
     */
    public V remove(Object key) {
        Iterator<Entry<K, V>> it = entrySet().iterator();
        Entry<K, V> current = null;
        if (key == null) {
            while (it.hasNext()) {
                Entry<K, V> e = it.next();
                if (e.getKey() == null) {
                    current = e;
                    break;
                }
            }
        } else {
            while (it.hasNext()) {
                Entry e = it.next();
                if (key.equals(e.getKey())) {
                    current = e;
                    break;
                }
            }
        }
        V oldValue = null;
        if (current != null) {
            oldValue = current.getValue();
            it.remove();//移除元素
        }
        return oldValue;
    }

    @Override
    public void clear() {
        entrySet().clear();
    }

    /**
     * 重写map的equals方法，使其比较元素值
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractMap)) {
            return false;
        }

        Map<?, ?> map = (Map<?, ?>) o;
        if (map.size() != this.size()) {
            return false;
        }

        //遍历比较值
        Iterator<Entry<K, V>> i = entrySet().iterator();
        while (i.hasNext()) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();

            if (value == null) {
                if (!(map.get(key) == null && map.containsKey(key)))
                    return false;
            } else {
                if (!value.equals(map.get(key)))
                    return false;
            }

        }
        return true;
    }


    /**
     * 共用的抽象迭代器
     */
    abstract class AbstractIterator {

        Iterator<Entry<K, V>> i = entrySet().iterator();

        public boolean hasNext() {
            return i.hasNext();
        }

        public void remove() {
            i.remove();
        }
    }

    /**
     * 供keySet集合使用的迭代器，Values则直接使用匿名内部类
     * <p>
     * 否定values使用匿名内部类，无法解决泛型问题，
     */
    class KeysIterator extends AbstractIterator implements Iterator<K> {

        @Override
        public K next() {
            return i.next().getKey();
        }
    }

    class ValuesIterator extends AbstractIterator implements Iterator<V> {

        @Override
        public V next() {
            return i.next().getValue();
        }
    }
}
