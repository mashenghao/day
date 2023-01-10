package bClassLoader;




import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 线程上下文类加载器：
 * <p>
 * jvm的双亲委托模型对于SPI（service provider interface ）中的服务类，无法进行支持，
 * 接口使jdk中的核心类，有启动类加载器加载，厂商提供的实现则是由系统加载器加载，由于命名空间，
 * 子命名空间对父命名空间是不可见的，实现类对接口类也是不可见的。线程上下文类加载器则是，
 * 父ClassLoader可以使用当前线程Thread.currentThread().getContextClassLoader()指定的
 * 类加载器加载类，这就改变了父ClassLoader加载的类不能使用子加载器的类或者其他没有父子关系的
 * 加载器加载的类的情况，改变了双亲委托模型。
 *
 * 使用模式： 获取--使用--还原
 *
 *  ClassLoader loader = Thread.currentThread().getContextClassLoader();
 *
 *  try{
 *      Thread.currentThread().setContextClassLoader(TargetTccl);
 *      // .... 执行加载类操作
 *      myMethod();
 *  }finally{
 *      Thread.currentThread().setContextClassLoader(loader);
 *  }
 *
 *  myMethod()里面调用了Thread.currentThread().getContextClassLoader()，获取当前线程的上下文类加载器
 *  去做某些事情。如果一个类由加载器A加载，那么这个类的依赖类也由相同的类加载器加载的（如果类没有被加载过），
 *  contextClassLoader就是为了破坏双亲委托机制。
 *
 *  当高层提供了统一的接口让底层去实现，同时又要在高层加载（或实例化）底层的类，就必须通过线程上下文类加载器去
 *  帮助高层找到并加载该类。
 *
 */
public class Demo10 {

    public static void main(String[] args) throws  Exception {

        Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "123456");
        System.out.println(conn);
        System.out.println(conn.getClass().getClassLoader());
    }


}
