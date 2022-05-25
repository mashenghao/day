package interview;

/**
 * String 、StringBuffer 、StringBuild 分析
 * <p>
 * String 是不可变的，被final修饰，是线程安全的
 * StringBuffer 是可变的，内部被synchronized同步，是线程安全的
 * StringBuild是可变的，线程不安全的。
 *
 * @author: mahao
 * @date: 2019/8/20
 */
public class BStringAndBufferAndBuild {
    /**
     * **可变性**
     * <p>
     * 简单的来说：`String` 类中使用 final 关键字修饰字符数组来保存字符串，`private　final　char　value[]`，所以 String 对象是不可变的。而StringBuilder 与 StringBuffer 都继承自 `AbstractStringBuilder` 类，在 `AbstractStringBuilder` 中也是使用字符数组保存字符串`char[]value` 但是没有用 final 关键字修饰，所以这两种对象都是可变的。
     * <p>
     * StringBuilder 与 StringBuffer 的构造方法都是调用父类构造方法也就是 `AbstractStringBuilder` 实现的，大家可以自行查阅源码。
     * <p>
     * **线程安全性**
     * <p>
     * String 中的对象是不可变的，也就可以理解为常量，线程安全。`AbstractStringBuilder` 是 StringBuilder 与 StringBuffer 的公共父类，定义了一些字符串的基本操作，如 `expandCapacity、append、insert、indexOf` 等公共方法。StringBuffer 对方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。StringBuilder 并没有对方法进行加同步锁，所以是非线程安全的。
     * <p>
     * **性能**
     * <p>
     * 每次对 String 类型进行改变的时候，都会生成一个新的 String 对象，然后将指针指向新的 String 对象。StringBuffer 每次都会对 StringBuffer 对象本身进行操作，而不是生成新的对象并改变对象引用。相同情况下使用 StringBuilder 相比使用 StringBuffer 仅能获得 10%~15% 左右的性能提升，但却要冒多线程不安全的风险。
     * <p>
     * **对于三者使用的总结：**
     * <p>
     * 1. 操作少量的数据: 适用String
     * 2. 单线程操作字符串缓冲区下操作大量数据: 适用StringBuilder
     * 3. 多线程操作字符串缓冲区下操作大量数据: 适用StringBuffer
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "aa";
        StringBuffer buffer = new StringBuffer("bb");
        StringBuilder builder = new StringBuilder("cc");
        String s2 = s.substring(1);
        StringBuffer buffer1 = buffer.append("bb");
        StringBuilder builder1 = builder.append("cc");
        //string类型不可变，s1输入新创建的对象，其余两个是可变的对象，在本身进行修改。
        System.out.println("s:" + s + ", s2:" + s2 + ", == " + (s == s2));
        System.out.println("buffer:" + buffer + ", buffer1:" + buffer1 + ", == " + (buffer == buffer1));
        System.out.println("builder:" + builder + ", builder1:" + builder1 + ", == " + (builder == builder1));

    }
}
