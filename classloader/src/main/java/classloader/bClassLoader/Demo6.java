package classloader.bClassLoader;

import org.junit.Test;

/**
 * 自定义类加载器在复杂类加载情况下的分析:
 * <p>
 * 一个类中，含有另一个类的引用，则使用的类加载机制则是，先加载外部类，
 * 用外部类的加载器去加载，这个类加载器同样采用的机制是双亲委托机制，还是先去
 * 寻找父加载器能加载的，如果找不到，使用最低的加载器。对于类中的其他类引用，采用的也是
 * 这个加载器，在加载引用类的时候，也是采用双亲委托机制。
 * <p>
 * <p>
 * 案例：
 * Person 类中 有个Cat宠物类，去加载
 */
public class Demo6 {

    /**
     * 1.创建一个ClassLoader，双亲加载器是SystemClassLoader，
     * 不指定路径，让他去加载Person类.
     * 2.增加jvm参数-XX:+TraceClassLoading，打印类加载信息，可以得知Cat类也被加载
     * 3. 打印结果 class bean.Person  sun.misc.Launcher$AppClassLoader@18b4aac2
     *
     *  分析：可以知道，由于双亲委托机制，SystemClassLoader可以加载类，所以加载的类都是有
     */
    @Test
    public void demo1() throws Exception {

        Demo4.MyClassLoader2 classLoader = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        Class<?> clazz = classLoader.loadClass("bean.Person");
        System.out.println(clazz + "  " + clazz.getClassLoader());
        /*
            验证2：验证类加载是如果类实例未被主动使用，即时加载错误，也不会发生错误，只有在初始化是，
            才会报错误。通过 clazz.newInstance(); 来验证。

            存在 clazz.newInstance()时，则cat.class必须存在。
         */
        clazz.newInstance();


    }

    /**
     * 测试环境2：
     * 1.如果demo1() 的案例不便，将Cat.class删除，则会出现的的情况？
     * 答案：会报ClassNotFound错误，因为在ClassPath下面找不到类，则会爆出错误。  java.lang.NoClassDefFoundError: bean/Cat
     * 2.Person.class和Cat.class完整，为自定义的classloader指定加载目录，则采用的加载器是？
     * 答案：将会还是SystemclassLoader因为父加载器下可以加载这两个class
     * <p>
     * 重点：
     * 3.如果将Cat放置在自定义的配置的路径下，设置加载目录，则运行的情况会是？
     * 3.1 在桌面新建文件夹bean，存放Cat.class文件，ClassPath下有Person.class文件
     * 3.2 指定classloader的加载路径
     * 答案： 会报类找不到（java.lang.NoClassDefFoundError: bean/Cat）,原因：ClassPath下有Person.class,自定义的类加载器下有
     * Cat.class,先加载Person.class，系统加载器可以加载，则这次加载器使用的加载器是SystemClassLoader，不会再去使用子类加载器了，
     * Cat.class在子类加载器哪儿，则加载不到。
     *
     * @throws Exception
     */
    @Test
    public void demo2() throws Exception {

        Demo4.MyClassLoader2 classLoader = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        classLoader.setPath("C:\\Users\\maho\\Desktop\\");
        Class<?> clazz = classLoader.loadClass("bean.Person");
        System.out.println(clazz + "  " + clazz.getClassLoader());
        clazz.newInstance();

    }

    /**
     * 案例：跨命名空间，本类使用自定义加载器，引用类是用系统加载类，引用类访问不了本类
     * 1.当指定加载路径，并且路径下有两个文件，则加载器都使用自定的加载器。
     * <p>
     * 2.当指定加载路径，路径下有Person.class,ClassPath下有Cat.class,则执行的加载器是
     * Person.class由自定义加载器加载，Cat.class由系统加载器加载。
     * 分析： 由于Person.class是有MyClassLoader加载的，则默认使用的加载器则是自定义加载器，
     * 在加载Cat.class时，由于SystemclassLoader可以加载。
     *
     * @throws Exception
     */
    @Test
    public void demo3() throws Exception {

        Demo4.MyClassLoader2 classLoader = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        classLoader.setPath("C:\\Users\\maho\\Desktop\\");
        Class<?> clazz = classLoader.loadClass("bean.Person");
        System.out.println(clazz + "  " + clazz.getClassLoader());
        clazz.newInstance();

    }

    /**
     * 案例：
     * 环境是：桌面上存在Person类，ClassPath下存在Cat类，则加载Person的加载器是自定义加载器，加载
     * cat的加载器是系统加载器。在他们各自的构造方法里面，访问对方的class对象，观察情况？
     * <p>
     * 答案：在Person中访问Cat正常访问，在Cat中访问Person出现错误，java.lang.NoClassDefFoundError: bean/Person
     * <p>
     * 分析：
     * 两个不同的类加载器加载的。他们不在同一个命名。 cat所在的命名空间为系统类加载器的命名空间，看
     * 不到子类加载器的命名空间。
     *
     * @throws Exception
     */
    @Test
    public void demo4() throws Exception {

        Demo4.MyClassLoader2 classLoader = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        classLoader.setPath("C:\\Users\\maho\\Desktop\\jvmOut\\");
        Class<?> clazz = classLoader.loadClass("bean.Person");
        System.out.println(clazz + "  " + clazz.getClassLoader());
        clazz.newInstance();

    }
}
