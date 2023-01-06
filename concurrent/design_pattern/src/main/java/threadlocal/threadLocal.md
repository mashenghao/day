#### 1. Thread中的ThreadLocalMap的Entry的key为啥要是虚引用

```java
Entry继承自WeakReference
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k); // k也就是threadLocal是虚引用的引用值
                value = v;
            }
        }
```

**也就是当发生GC时，如果ThreadLocal实例没有外部的强引用，Entry中的key将置为null。  整个流程也就是，我们会自己在BeanA中创建个ThreadLocal的对象去使用，如果这个BeanA被回收了，或者在BeanA将threadLocal置为null，则此时就没有ThreadLocal的强引用的，GC是Entry的key就会置为null。**



```
为啥ThreadLocalMap中ThreadLocal要是虚引用？ 

当BeanA被GC回收后，此时ThreadLocal已经没有引用了，按理说该被回收掉。 但是，thread中的map中却存在这个threadlocal的引用，如果map中还是强引用的话，这个threadLoca就一直无法被回收掉，在线程池的线程情况下。
那如果我们使用了弱引用，就不会有这个问题。当栈中的ThreadLocal变量置为null后，堆中的ThreadLocal对象只有一个Entry的弱引用指向，下一次垃圾回收的时候堆中的ThreadLocal对象就会被回收，这就是Entry要继承WeakReference的原因。
```



#### 2. ThreadLocal内存泄漏问题

用完ThreadLocal后，没有进行remove操作，之后将ThreadLocal置为null，或者BeanA回收掉， **此时，ThreadLocalMap中就会存在这个threadlocal的一个Entry， 这个entry的key为null(被虚引用GC掉了)，value不为空， 出现了内存泄漏。**

针对这种map中key为null的情况，做了清理处理，但是不能清理全部：

```
ThreadLocalMap对key为null的entry，在调用set  get  remove方法时，会进行二次hash定位查询，当定位比较时，如果key为null，就会清理掉这个entry。  如果一直不去调用这些方法，或者没有hash冲突，那些null key一直无法清理掉。
```



