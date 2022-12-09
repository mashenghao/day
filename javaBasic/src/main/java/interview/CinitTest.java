package interview;

/**
 * 类初始化的时候，如果静态代码块报错，之后再次使用这个类会报错吗
 *
 * @author mahao
 * @date 2022/12/07
 */
public class CinitTest {


    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        System.out.println("1");

        //触发类加载
        try {
            StaticClass staticClass = StaticClass.class.newInstance();
        } catch (ExceptionInInitializerError error) {
            System.out.println("手动捕获这个error" + error.getMessage());
        }

        //Exception in thread "main" java.lang.NoClassDefFoundError: Could not initialize class interview.CinitTest$StaticClass
        //在此尝试加载这个类，将会报错NoClassDefFoundError， 抛出的是定义错误。
        StaticClass staticClass = StaticClass.class.newInstance();


    }

    public static class StaticClass {

        static {
            System.out.println("staticClass 方法被执行前 ");
            if (1 == 1)
                throw new RuntimeException("手动在静态代码块抛出一个异常！！！");
        }

    }
}
