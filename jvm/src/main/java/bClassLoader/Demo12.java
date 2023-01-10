package bClassLoader;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 通过JDBC驱动加载深刻理解线程上下文类加载机制
 */
public class Demo12 {


    public static void main(String[] args) throws Exception {
        /*
        执行：传入类的二进制名，通过反射生成Driver的Class对象。
        具体：
            1.获取调用类的classLoader
            2.使用调用类的类加载器去加载Class对象
         */
        Class.forName("com.mysql.jdbc.Driver");
        /*
        执行：使用DriverManager获取连接，拥有两个操作，一个是注册驱动，一个是获取驱动。
        注册驱动：
            1.DriverManager静态代码块执行，初始化注册程序
            2.通过ServiceLoader类，去加载服务提供商的类（关于ServiceLoader的加载查看Demo11）
            3.扫描系统是否设置加载的驱动类，通过参数（jdbc.drivers）
        获取连接：
            1.验证权限，就是当前调用类的类加载器是否和驱动的类加载器相同
            2.在缓存的driver类中，获取连接。
         */
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "123456");

    }
}
