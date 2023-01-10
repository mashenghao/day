package bClassLoader;

import java.io.*;

/**
 * @program: jvn
 * @Date: 2019/7/9 10:09
 * @Author: mahao
 * @Description: 自定义类加载器，指定父加载器为ExtClassLoader，去自己加载CLASSPTH下class文件
 */
public class MyClassloader extends ClassLoader {

    private String classLoaderName;
    private String fileExtention = ".class";

    public MyClassloader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public MyClassloader(String classLoaderName) {
        super(ClassLoader.getSystemClassLoader());
        this.classLoaderName = classLoaderName;
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
            in = new FileInputStream("out\\production\\jvn\\" + new File(name.replace(".", "\\") + fileExtention));

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


    @Override
    public String toString() {
        return "MyClassloader{" +
                "classLoaderName='" + classLoaderName + '\'' +
                ", fileExtention='" + fileExtention + '\'' +
                '}';
    }

    public static void main(String[] args) throws ClassNotFoundException {


        System.out.println("使用默认的加载器加载");
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> aClass1 = loader.loadClass("aActiveUse.Singleton");
        System.out.println(aClass1.hashCode());

        ClassLoader loader2 = ClassLoader.getSystemClassLoader();
        Class<?> aClass2 = loader.loadClass("aActiveUse.Singleton");
        System.out.println(aClass2.hashCode());

        /* 可以发现使用默认的加载器加载了两次类，得出的class对象是同一个，
         而使用自定义加载的ClassLoader创建了两个classLoader
         生成的class对象含有两个， */


        System.out.println("使用自定义的加载器加载");

        ClassLoader classLoader1 = new MyClassloader(ClassLoader.getSystemClassLoader().getParent(), "myClassloader");
        Class<?> aClass = classLoader1.loadClass("aActiveUse.Singleton");
        System.out.println(aClass.hashCode());

        ClassLoader classLoader2 = new MyClassloader(ClassLoader.getSystemClassLoader().getParent(), "myClassloader");
        Class<?> bClass = classLoader2.loadClass("aActiveUse.Singleton");
        System.out.println(bClass.hashCode());
    }
}
