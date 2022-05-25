package classloader.bClassLoader;

import org.junit.Test;

import java.io.*;

/**
 * 类加载器的命名空间：
 * 1.每个类加载器都有自己的命名空间，命名空间由加载器及所有的
 * 父加载器所加载的类组成。
 * 2.在同一个命名空间中，不会出现类的完整名字（包括类的包名）相同的类。
 * 3.在不同的命名空间中，有可能出现完整名字相同的两个类。
 */
public class Demo4 {


    //测试在同一个命名空间里面，一个类只被加载一次
    //当SystemClassloader能够在CLASSPATH中找到文件，就由父加载器SystemClassloader去加载，
    //并且只加载一次
    @Test
    public void demo1() throws ClassNotFoundException {
        MyClassLoader2 loader1 = new MyClassLoader2(ClassLoader.getSystemClassLoader());
        MyClassLoader2 loader2 = new MyClassLoader2(ClassLoader.getSystemClassLoader());
       /* loader1.setPath("C:\\Users\\maho\\Desktop\\");
        loader2.setPath("C:\\Users\\maho\\Desktop\\");*/
        Class<?> class1 = loader1.loadClass("aActiveUse.Singleton");
        Class<?> class2 = loader2.loadClass("aActiveUse.Singleton");
        System.out.println(class1.hashCode());
        System.out.println(class2.hashCode());
    }

    /**
     * 当删除了ClassPath里面的Singleton.class，则双亲委托机制下，父加载器加载不到，
     * 自定义加载器去加载，由于创建了两个加载器，所以类会被加载两次，在各自的命名空间内
     * ，并且不冲突。
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void demo2() throws ClassNotFoundException {
        MyClassLoader2 loader1 = new MyClassLoader2(ClassLoader.getSystemClassLoader());
        MyClassLoader2 loader2 = new MyClassLoader2(ClassLoader.getSystemClassLoader());
        loader1.setPath("C:\\Users\\maho\\Desktop\\");
        loader2.setPath("C:\\Users\\maho\\Desktop\\");
        Class<?> class1 = loader1.loadClass("aActiveUse.Singleton");
        Class<?> class2 = loader2.loadClass("aActiveUse.Singleton");
        System.out.println(class1.hashCode());
        System.out.println(class2.hashCode());
    }


    /**
     * 简易版的classloader
     */
    public static class MyClassLoader2 extends ClassLoader {

        private String path = "";

        public MyClassLoader2(ClassLoader parent) {
            super(parent);
        }

        public void setPath(String path) {
            this.path = path;

        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            System.out.println("执行自定义的类加载器" + name);
            byte[] bytes = loadClassData(name);
            return defineClass(name, bytes, 0, bytes.length);
        }

        private byte[] loadClassData(String name) {

            InputStream in = null;
            try {
                in = new FileInputStream(new File(path + name.replace(".", "\\") + ".class"));

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int n = 0;
                while ((n = in.read()) != -1) {
                    out.write(n);
                }
                byte[] buf = out.toByteArray();
                return buf;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

}
