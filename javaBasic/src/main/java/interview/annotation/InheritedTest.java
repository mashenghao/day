package interview.annotation;

import java.lang.reflect.Method;

/**
 * 注解被子类继承测试
 * 这个@Inherited元注解，只有在类上的才有用，其他作用在方法上没用，子类重写也必须标记该注解。
 * <p>
 * 被标记了@Inherited元注解的注解，在寻找时，如果当前类找不到，就会向父类寻找，直至找到Object类为止。
 * 接口 与 方法 上标记的注解无法被继承。
 * <p>
 * http://c.biancheng.net/view/7009.html
 *
 * @author mahao
 * @date 2022/12/30
 */
@Lock
public class InheritedTest {

    /*
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Inherited
    public @interface Lock {
    }
     */
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = InheritedTest.class.getDeclaredMethod("m1");
        Lock annotation = method.getAnnotation(Lock.class);
        System.out.println(annotation); //返回注解
        System.out.println(InheritedTest.class.getAnnotation(Lock.class)); //返回注解
        System.out.println("//////////////////////");

        //2. 注解被@Inherited标记，表示注解可以被继承, 方法无法继承注解
        Method method2 = SonClass.class.getDeclaredMethod("m1");
        Lock annotation2 = method2.getAnnotation(Lock.class);
        System.out.println(annotation2);//返回null，因为方法没有继承能力
        System.out.println(SonClass.class.getAnnotation(Lock.class)); //类会被继承，返回注解

        //3.接口无法继承，只有类能继承
        System.out.println("///////////////////子类无法继承接口的注解///////////////");
        System.out.println(CA.class.getAnnotation(Lock.class));

    }

    @Lock
    public void m1() {

    }

    static class SonClass extends InheritedTest {

        @Override
        public void m1() {
            super.m1();
        }
    }


    @Lock
    interface IA {

    }

    static class CA implements IA {

    }
}
