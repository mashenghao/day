package bClassLoader;


import java.sql.DriverManager;

/**
 * 获取加载器的途径
 */
public class Demo2 {

    public static void main(String[] args) {
        //获得当前类的classloader
        ClassLoader loader1 = Demo2.class.getClassLoader();
        System.out.println(loader1);

        //获得当前线程上下文的classloader
        ClassLoader loader2 = Thread.currentThread().getContextClassLoader();
        System.out.println(loader2);

        //获得系统的classloader
        ClassLoader loader3 = ClassLoader.getSystemClassLoader();
        System.out.println(loader3);

        //获得调用者的classloader
        ClassLoader loader4 = DriverManager.class.getClassLoader();
        System.out.println(loader4);
    }
}
