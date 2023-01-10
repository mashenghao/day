package bClassLoader;

import org.junit.Test;

/**
 * 类加载器各自加载的文件目录
 */
public class Demo7 {

    public static void main(String[] args) {
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
        ClassLoader.getSystemClassLoader();
    }

    /**
     * 让根加载器去加载Singleton.class文件，需要将文件放到指定的加载目录
     */
    @Test
    public void demo1() throws ClassNotFoundException {
        Demo4.MyClassLoader2 loader = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        Class<?> clazz = loader.loadClass("aActiveUse.Singleton");
        System.out.println(clazz + "  " + clazz.getClassLoader());
    }
}
