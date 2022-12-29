package interview.methodHandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * MethodHandle  与 Java反射 的不同：
 * <p>
 * 1. 两者都是在模拟方法的调用，反射模拟的是java代码层次的调用，mh模拟的是字节码层次的调用。在MethodHandles.Lookup上的3个方法
 * findStatic()、findVirtual()、findSpecial()正是为了对应于invokestatic、invokevirtual（以及
 * invokeinterface）和invokespecial这几条字节码指令的执行权限校验行为，而这些底层细节在使用Reflection API时是不需要关心的
 * <p>
 * 2.Reflection中的java.lang.reflect.Method对象远比MethodHandle机制中的
 * java.lang.invoke.MethodHandle对象所包含的信息来得多。前者是方法在Java端的全面映像，包含了方法
 * 的签名、描述符以及方法属性表中各种属性的Java端表示方式，还包含执行权限等的运行期信息。而
 * 后者仅包含执行该方法的相关信息。
 * <p>
 * 3.由于MethodHandle是对字节码的方法指令调用的模拟，那理论上虚拟机在这方面做的各种优化
 * （如方法内联），在MethodHandle上也应当可以采用类似思路去支持（但目前实现还在继续完善
 * 中），而通过反射去调用方法则几乎不可能直接去实施各类调用点优化措施。
 * <p>
 * 4.“仅站在Java语言的角度看”之后：Reflection API的设计目标是只为Java语言服务的，而MethodHandle
 * 则设计为可服务于所有Java虚拟机之上的语言，其中也包括了Java语言而已，而且Java在这里并不是主角。
 *
 * @author mahao
 * @date 2022/12/29
 */
public class MhTest {

    public static void main(String[] args) throws Throwable {
        //调用类的方法
        ClassA classA = new ClassA();
        Object zs = getPrintlnMh(classA).invoke("zs");
        System.out.println(zs);

        //调用标准输出的方法
        getPrintlnMh(System.out).invoke("ls");

    }

    /**
     * 获取方法println的方法句柄
     *
     * @param obj
     * @return
     */
    public static MethodHandle getPrintlnMh(Object obj) throws NoSuchMethodException, IllegalAccessException {
        //1. 表示方法的类型；第一个是返回值，之后的是参数类型
        MethodType methodType = MethodType.methodType(void.class, String.class);

        //2.lookup用来查找指定类中符合给定的方法名，参数 返回值，访问权限的方法句柄的
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        //3.查找的是虚方法，按照java语言规则，第一个参数是隐式的，代表了方法的接受者，就是this。
        return lookup.findVirtual(obj.getClass(), "println", methodType).bindTo(obj);

    }

    static class ClassA {
        public void println(String str) {
            System.out.println(str);
        }
    }
}
