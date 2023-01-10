package bClassLoader;

/**
 * 使用自定义的类加载器实现类的卸载
 */
public class Demo5 {

    /**
     * 当类加载器和Class对象，都没有引用指向时，就会对类进行卸载，jvm自带的类加载器，由于始终，
     * 会由java虚拟机引用，这些加载器又会始终引用他们加载的class对象。但是，自定义的类加载则可能都会有
     * 指向为空的情况，所以是可以卸载的。
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader1 = new MyClassloader(ClassLoader.getSystemClassLoader().getParent(), "myClassloader");
        Class<?> aClass = classLoader1.loadClass("aActiveUse.Singleton");
        System.out.println(aClass.hashCode());
        /*aClass = null; 方法一
        classLoader1 = null;*/

        /*方式二*/
        classLoader1 = ClassLoader.getSystemClassLoader();
        aClass = classLoader1.loadClass("aActiveUse.Singleton");
        System.gc();

    }
}
