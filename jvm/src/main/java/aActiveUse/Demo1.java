package aActiveUse;

/**
 * 静态变量字段，只有直接定义了该字段的类才会被初始化
 *  -XX：+TraceClassLoading 用于追踪类的加载信息打印
 *
 * 1.Child.p运行，那些类被初始化？
 *
 * 2.Child.s运行，那些类被初始化？
 *
 * 3.Child.p运行，Child类会不会被加载？
 *
 *
 */
public class Demo1 {

    public static void main(String[] args) {
        //如果将Child.p换成Child.s呢？？
        System.out.println(Child.p);
    }
}

class Parent {
    static String p = "parent";

    static {
        System.out.println("parent 被初始化 ");
    }
}

class Child extends Parent {
    static String s = "child";

    static {
        System.out.println("child 被初始化 ");
    }
}
