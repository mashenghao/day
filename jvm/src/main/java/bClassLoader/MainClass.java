package bClassLoader;

/**
 * 博文解析
 */
public class MainClass {

    public static void main(String[] args) throws ClassNotFoundException {
      //获取系统默认的类加载器（SystemClassLoader）
      ClassLoader classLoader = ClassLoader.getSystemClassLoader();
      //输入class的限定名
      Class<?> clazz = classLoader.loadClass("bean.Person");
    }
}
