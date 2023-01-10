package chapter8_class;

import cn.chapter8_class.classObjectClass.MyClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2020/10/28
 */
public class MianClass {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {
        //反射直接获取class继承的抽象方法，直接使用即可，直接在class中实现了抽象方法。
        Class clazz1 = Class.forName("cn.chapter8_class.classObjectClass.MyClass");
        MyClass myClass = (MyClass) clazz1.newInstance();
        myClass.work();

        //对于object实现抽象方法，必须要获取伴生类的实例，如果不带$则是最终类，里面没有work的实现。
        //也无法将其强转为父类对象，因为没有继承抽象父类。
        Class clazz = Class.forName("cn.chapter8_class.classObjectClass.MyObject$");
        Field field = clazz.getField("MODULE$");
        AbsClassI absClassI = (AbsClassI) field.get(clazz);
        absClassI.work();

        // 3.利用object中方法，在最终class中也有一个方法签名，所有可以利用这个特性，反射获取
        //这个方法去执行。
        Class clazz2 = Class.forName("cn.chapter8_class.classObjectClass.MyObject");
        for (Method method : clazz2.getMethods()) {
            if (method.getName().equalsIgnoreCase("work")) {
                method.invoke(null, null);
            }

        }

    }
}
