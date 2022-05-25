package classloader.bClassLoader;


import classloader.demo.AA;

/**
 * @program: jvn
 * @Date: 2019/7/29 16:23
 * @Author: mahao
 * @Description:
 */
public class Demo13 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
        ClassLoader classLoader2 = classLoader1.getParent();
        //使用系统类加载器加载实现类
        Class<?> aClass = classLoader1.loadClass("demo.AAImol");
        Object instance = aClass.newInstance();
        //打印创建的实例的类加载器，和静态类型的类加载器实例
        System.out.println("实例的加载器： " + instance.getClass().getClassLoader() + "--- 引用类型的加载器" + Object.class.getClassLoader());
        //转为其他类型数据，由父类加载器加载的实例
        AA aa = new AA() {
            @Override
            public void run() {
                System.out.println("123");
            }
        };
        aa = (AA) instance;
        aa.run();
        System.out.println("AA实现类加载是有---" + aa.getClass().getClassLoader());
    }
}
