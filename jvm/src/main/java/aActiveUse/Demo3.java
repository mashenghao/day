package aActiveUse;

import java.util.UUID;

/**
 * 编译器常量与运行期常量的区别:
 * 对于运行期的常量，在编译期间是无法确定的，只能在程序运行期间才能确定，所以，需要
 * Parent3初始化。
 *
 * <p>
 * 1.执行parent.p ，Parent3类是否被初始化？
 * <p>
 * 2.执行parent.p2，Parnet3类是否被初始化？
 */
public class Demo3 {

    public static void main(String[] args) {
        System.out.println(Parent3.p2);
    }
}

class Parent3 {

    public final static String p = "parent";

    public final static String p2 = UUID.randomUUID().toString();

    static {
        System.out.println("Parent 被初始化");
    }
}