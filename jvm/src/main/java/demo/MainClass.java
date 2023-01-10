package demo;

import bClassLoader.Demo4;

/**
 * @program: jvn
 * @Date: 2019/7/8 8:57
 * @Author: mahao
 * @Description:
 */
public class MainClass {

    public static void main(String[] args) throws Exception {

        Demo4.MyClassLoader2 classLoader = new Demo4.MyClassLoader2(ClassLoader.getSystemClassLoader());
        classLoader.setPath("C:\\Users\\maho\\Desktop\\");
        Class<?> clazz = classLoader.loadClass("demo.AAImol");

        AA aa = (AA) clazz.newInstance();
        AA aa2 = new AA() {
            @Override
            public void run() {

            }
        };

        System.out.println(aa.getClass().getClassLoader());
        System.out.println(AA.class.getClassLoader());
        System.out.println(aa2.getClass().getClassLoader());

        
    }
}

