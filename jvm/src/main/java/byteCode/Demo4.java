package byteCode;

/**
 * 栈帧：
 * <p>
 * 栈帧是用于支持虚拟机进行方法调用和方法执行的数据结构，他是虚拟机运行时数据区的虚拟机栈
 * 栈的栈元素。栈帧存储了方法的局部变量表，操作数栈，动态链接和方法的返回地址信息。每一个
 * 方法从调用开始直至执行完成，都对应的一个栈帧在虚拟机栈里入栈和出栈的过程，
 * <p>
 * 方法调用助记符：
 * 1.invokeinterface： 调用接口中的方法，实际上是在运行期间决定的，决定调用哪一个实现了接口的对象中的方法。
 * （涉及了多态的特性，父类引用调用哪个对象的方法，只有在运行期间才能确定）
 * 2.invokestatic： 调用静态方法。
 * 3.invokespecial：调用自己的私有方法，构造方法（<init>标记），父类的构造方法等。
 * 4.invokevirtual： 调用虚方法，运行期间动态查找的过程
 * 5.invokedynamic： 动态调用方法。
 * <p>
 * 符号引用 直接引用
 * <p>
 * 有些符号引用在类加载阶段或是第一次使用时就会转换为直接引用，这种转换叫做静态解析，；
 * 另外一些符号引用则是在每次运行期间转换为直接引用，这种转换叫做动态链接，这体现了
 * java的多态性。
 *
 * 静态解析的四种情形：（invokestatic，invokespecial）
 * 1.静态方法
 * 2.父类方法
 * 3.构造方法
 * 4.私有方法（不可以被重写）
 * 他们是在类加载阶段，就将符号应用转换为直接引用的。
 */
public class Demo4 {

    /**
     * 方法的调用助记符，通过invokestatic，参数是方法的常量池索引
     * 0 invokestatic #4 <byteCode/Demo4.method1>
     * 3 return
     */
    public static void method1() {
        System.out.println();
    }

    public static void main(String[] args) {
        new Demo4().method1();
    }

    public void method2() {
        System.out.println();
    }
}
