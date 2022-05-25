package classloader.bClassLoader;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 不同命名空间内的类实例，是不相等的
 */
public class Demo8 {

    /**
     * 1.声明了两个类加载器，指定加载路径是桌面上的,都有自定义加载，所以clazz1和clazz2不在同一个命名空间内
     * 2.在加载的类中，设置方法，接收外部传来的对象，如果对象来自不同命名空间传来的对象，则会出现错误
     * ClassCastException: bean.Demo8User cannot be cast to bean.Demo8User
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void demo1() throws Exception {
        Demo4.MyClassLoader2 loader1 = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        Demo4.MyClassLoader2 loader2 = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        loader1.setPath("C:\\Users\\maho\\Desktop\\");
        loader2.setPath("C:\\Users\\maho\\Desktop\\");
        Class<?> clazz1 = loader1.loadClass("bean.Demo8User");
        Class<?> clazz2 = loader2.loadClass("bean.Demo8User");

        System.out.println("两个类对象是否相等   " + (clazz1 == clazz2));
        Object obj1 = clazz1.newInstance();
        Object obj2 = clazz2.newInstance();

        System.out.println("clazz1 classloader is--" + clazz1.getClassLoader());
        System.out.println("clazz2 classloader is--" + clazz2.getClassLoader());

        /**
         *     public void setUser(Demo8User demo8User) {
         *         this.user = demo8User;
         *         System.out.println("111111111111111");
         *     }
         *
         *     public void setUser(Object obj) {
         *         this.user = (Demo8User) obj;
         *         System.out.println("222222222222");
         *
         *     }
         *
         *     在加载的类中，设置了两个方法，测试前提是clazz1 和 clazz2 有不同的命名空间。
         *     1.当获取方法setUser(Object obj)，为user赋值Object对象，当参数是obj2，是另一个空间的对象实例，则会发生错误
         *          Caused by: java.lang.ClassCastException: bean.Demo8User cannot be cast to bean.Demo8User，可以看到是
         *       实例名相同，但由于类加载器不同，导致实例无法相互转换。如果传入参数是obj1则可以正常运行，因为是同一命名空间的
         *      同一类型。
         *      <p>
         *      2.当获取setUser(Demo8User obj)时，如果传入的参数是Method method = clazz1.getMethod("setUser", Demo8User.class);，
         *      则会出现错误Caused by: java.lang.ClassNotFoundException: bean.Demo8User，
         *      解释1： 因为这里的Demo8User.class，是loader2加载的类，所以，找不到clazz2对象.
         *      解释2：这里的Demo8User.class被删除了，所以在getMethod时，重新加载时候，加载不到，会报找不到异常。
         *      要想获取方法，需要制定参数的class为clazz1，即loader1加载的class对象。
         *       <p>
         *      3.保证2的加载正确，测试method.invoke(obj1, obj？);当放入obj1和obj2之间会有什么不同，
         *      obj1,显然执行正常，方法时clazz1对象的，参数也是clazz1的实例所以执行正常
         *      obj2，执行错误，会报非法参数，java.lang.IllegalArgumentException: argument type mismatch，因为clazz1对象
         *      的方法接受的是clazz1对象实例，而传入的是clazz2的对象实例。
         *
         */
        Method method = clazz1.getMethod("setUser", Object.class);
        System.out.println(method);
        method.invoke(obj1, obj2);
    }
}
