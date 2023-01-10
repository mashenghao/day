package bClassLoader;

/**
 * 类加载器的父类
 */
public class Demo1 {

    public static void main(String[] args) {

        ClassLoader c1 = Demo1.class.getClassLoader();
        System.out.println(c1);

        ClassLoader c2 = c1.getParent();
        System.out.println(c2);

        ClassLoader c3 = c2.getParent();
        System.out.println(c3);
    }
}
