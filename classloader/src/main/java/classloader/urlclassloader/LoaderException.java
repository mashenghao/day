package classloader.urlclassloader;

/**
 * class的生命周期：
 * 加载 -> 链接(验证、准备、解析) -> 初始化 -> 使用 -> 卸载
 * 这里的几个阶段是按顺序开始，而不是按顺序进行或完成，因为这些阶段通常都是互相交叉地混合进行的，
 * 通常在一个阶段执行的过程中调用或激活另一个阶段。
 * <p>
 * classloader在加载不到类时，会抛出ClassNotFoundException 或者 NoClassDefFoundError。
 * 1.ClassNotFoundException是在类加载阶段，classloader的findClass找不到目标类，而自己抛出
 * 的异常。
 * 2.NoClassDefFoundError是jvm在运行阶段或者在类加载阶段加载不到类而发生的，这个要加载的类，
 * 不是手动输入的，而是jvm自己去加载的类，在jvm中或者类路径下找不到，而抛出的异常。比如classloader
 * 去加载一个class文件，这个class文件有继承的父类，父类不在加载不到，当classloader加载这个类的时候，
 * 将会发生NoClassDefFoundError。
 * 3.单独使用类加载器加载一个有其他依赖的class文件，理论是不会发生错误的。 JVM规范允许类加载器在预料
 * 某个类将要被使用时就预先加载它，如果预先加载的类遇到了.class文件缺失或者存在错误，类加载器就必须在
 * 程序首次主动使用这个类的时候才报告错误(LinkageError).
 * 如果这个缺失.class的类一直没有被程序主动使用，那么类加载器就不会报告错误.
 * <p>
 * 4.缺失父类，在解析阶段出现错误，解析阶段会触发关联的类进行加载。
 * 解析策略：
 * 策略1：链接 A 的时候发现引用了 B，因此加载 B
 * 策略2：链接 A 的时候发现引用了 B，但是 B 没有被使用，因此不加载 B。在真正使用 B 时才加载 B，例如 b = new B();
 *
 * @author: mahao
 * @date: 2020/12/8
 */
public class LoaderException {

    public static void main(String[] args) {
        m1();
    }

    public static void m1() {
        try {
            m2();
        } catch (Exception e) {
            //每次进行异常的包装，就是一个新的Throwable地方，尽量抛出原有异常，或者通过e.getCause获取原始错误异常。
            //throw e;
            //外层捕获的异常堆栈信息，将从这里从新算起，如果新创建的异常没有将实际异常作为cause，将会丢失异常信息
            throw new RuntimeException(e);
        }
    }

    public static void m2() {
        if (1 == 1)
            throw new IllegalArgumentException("非法参数异常");
    }
}
