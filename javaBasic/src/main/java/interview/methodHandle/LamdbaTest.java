package interview.methodHandle;

/**
 * lambda原理就是：
 * 1. 调用invokerDynamic指令，需要引导方法，这个引导方法就是固定的java.lang.invoke.LambdaMetafactory#
 * metafactory(java.lang.invoke.MethodHandles.Lookup, java.lang.String, java.lang.invoke.MethodType, java.lang.invoke.MethodType, java.lang.invoke.MethodHandle, java.lang.invoke.MethodType)，
 * 参数有方法接口类型，参数类型，返回类型，MethodHandle(实现方法的句柄，及时函数生成的lamdba$静态方法).
 * 2. 引导方法通过这些东西，构造出一个CallSite返回，jvm就会调用了。想一下，持有了lambda实现方法的MH，引导方法创建一个内部匿名的class字节码，然后构造成callsite返回，
 * 所以lamdba函数就能被找到然后调用了。
 * 3. 为啥不直接返回lamdba生成的函数的MethodHandle？？
 * 因为需要闭包， 如果lambda函数中使用到了外部的变量，就会生成的class的构造函数传进去。 在调用的时候，就能传给函数去用了。 直接用函数的句柄，无法做到。
 *
 * @author mahao
 * @date 2022/12/29
 */
public class LamdbaTest {

    public static void main(String[] args) {

        Ff ff = (a) -> {
            System.out.println(a);
            return "sfff";
        };

        ff.aa1(1);
    }

    @FunctionalInterface
    interface Ff {

        String aa1(Integer integer);

    }

}
