package dynamicCompiler;

import com.oracle.tools.packager.IOUtils;
import dynamicCompiler.p3.IRule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author mahao
 * @date 2022/12/08
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        DirClassLoader dirClassLoader =
                new DirClassLoader("C:\\Users\\bangsun\\Desktop\\动态编译\\classes");
        Class<?> aClass = dirClassLoader.loadClass("dynamicCompiler.p3.Rule");
        System.out.println(aClass); //class dynamicCompiler.p3.Rule

        IRule rule = (IRule) aClass.newInstance();
        boolean audit1 = rule.audit("1");
        System.out.println(audit1); //true

        boolean audit2 = rule.audit("0");
        System.out.println(audit2); //true

    }


    public static void main2(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        //ClassNotFoundException,因为这个类不存在classpath下。
//        Class<?> aClass = classLoader.loadClass("dynamicCompiler.p1.User");

        DirClassLoader dirClassLoader = new DirClassLoader("C:\\Users\\bangsun\\Desktop\\动态编译\\classes");
        Class<?> aClass = dirClassLoader.loadClass("dynamicCompiler.p1.User");
        System.out.println(aClass);


        /*
         * 不能在这里直接去使用User类，比如
         * User user = new User();
         * 因为当前类使用的ClassLoader是系统类加载器，系统加载器无法找到User类。
         *
         */
    }

    /**
     * 自定义一个加载指定目录的类加载器
     */
    static class DirClassLoader extends ClassLoader {

        private final String dir;

        public DirClassLoader(String dir) {
            this.dir = dir;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            String path = name.replaceAll("\\.", "/") + ".class";
            File classFile = new File(dir, path);
            if (!classFile.exists()) {
                throw new ClassNotFoundException(name);
            }
            try {
                byte[] bytes = IOUtils.readFully(classFile);
                //读取文件为字节数组，交由defineClass去将字节码二进制数组加载为Class对象
                return defineClass(name, bytes, 0, bytes.length);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
