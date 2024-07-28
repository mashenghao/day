package dynamicCompiler.p3;

/**
 * @author mash
 * @date 2022/12/08
 */
public class ClassNotFundMain {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        //ClassNotFoundException,因为这个类不存在classpath下。
        Class<?> aClass = classLoader.loadClass("dynamicCompiler.p1.User");
    }
}
