package dynamicCompiler.p5;

import dynamicCompiler.p4.DirClassLoader;
import dynamicCompiler.IRule;

/**
 * 子类加载器可以使用父类加载器 加载的类
 *
 * @author mash
 * @date 2022/12/08
 */
public class Main {

    public static void main(String[] args) throws Exception {

        DirClassLoader dirClassLoader =
                new DirClassLoader("/Users/getui/Desktop/compiler/classes");
        Class<?> aClass = dirClassLoader.loadClass("dynamicCompiler.p5.Rule");
        System.out.println("类型：" + aClass);
        System.out.println("父类型：" + aClass.getInterfaces()[0]);

        //反射生成一个实例，将父类IRule引用指向子类对象。
        //IRule
        IRule rule = (IRule) aClass.newInstance();

        boolean audit1 = rule.audit("1");
        System.out.println(audit1); //true

        boolean audit2 = rule.audit("0");
        System.out.println(audit2); //false
    }
}
