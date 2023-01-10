package bClassLoader;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * ServiceLoader在SPI中的重要作用分析
 */
public class Demo11 {

    /*
        运行看到，serviceLoader使用的是启动类加载器，按照双亲委托模型，Driver类使用的类加载器应该是
        BootStrapClassLoader，但时，使用的确实系统类加载器。
     */
    public static void main(String[] args) {
        /**
         * 驱动加载步骤：
         * 1.指定服务接口的class对象
         * 2.ServiceLoader为加载服务提供商设定上下文类加载器
         * 3.服务提供商在META-INF/services/ 创建与服务同名的文件，内容是服务实现的类名
         * 4.惰性加载扫描加载服务提供商的服务
         */
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Driver d = iterator.next();
            System.out.println(d + "----" + "类加载器名称" + d.getClass().getClassLoader());
        }
        System.out.println("上下文类加载器是" + Thread.currentThread().getContextClassLoader());
        System.out.println("serviceLoader类使用的类加载器是" + serviceLoader.getClass().getClassLoader());
    }
}
