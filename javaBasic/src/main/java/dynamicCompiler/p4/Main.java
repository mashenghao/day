package dynamicCompiler.p4;

/**
 * @author mash
 * @date 2022/12/08
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        //ClassNotFoundException,因为这个类不存在classpath下。
//        Class<?> aClass = classLoader.loadClass("dynamicCompiler.p1.User");

        DirClassLoader dirClassLoader = new DirClassLoader("/Users/getui/Desktop/compiler/classes");
        Class<?> aClass = dirClassLoader.loadClass("dynamicCompiler.p1.User");
        System.out.println(aClass);

        /*
         * 不能在这里直接去使用User类，比如
         * User user = new User();
         * 因为当前类使用的ClassLoader是系统类加载器，系统加载器无法找到User类。
         *
         */
    }
}
