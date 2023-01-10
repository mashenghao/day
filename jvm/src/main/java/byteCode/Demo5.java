package byteCode;

/***
 * * <p>
 *  * 符号引用 直接引用
 *  * <p>
 *  * 有些符号引用在类加载阶段或是第一次使用时就会转换为直接引用，这种转换叫做静态解析，；
 *  * 另外一些符号引用则是在每次运行期间转换为直接引用，这种转换叫做动态解析，这体现了
 *  * java的多态性。
 *  *
 *  * 静态解析的四种情形：（invokestatic，invokespecial）
 *  * 1.静态方法
 *  * 2.父类方法
 *  * 3.构造方法
 *  * 4.私有方法（不可以被重写）
 *  * 他们是在类加载阶段，就将符号应用转换为直接引用的
 */

/**
 * 方法重载与invokeVirtual字节码指令的关系：
 * <p>
 * 方法的静态分配：
 * <p>
 * Grandpa g1 = new Father();
 * <p>
 * 以上代码，g1的静态类型是Grandpa,而实际类型（真正指向的类型）是Father。
 * 结论：
 * 变量的静态类型是不会发生变化的，而变量的实际类型会随着实际类型的而变化（多态的体现），
 * 实际类型是在方法运行期间方可确定的。
 * <p>
 * 一个类中含有多个同名方法，参数个数、类型、顺序不同的方法是方法的重载方法，
 * 对于重载的方法调用，是一种静态行为，是根据参数的静态类型去调用重载方法。
 * `对于jvm，方法重载，是一种静态行为，是根据变量的类型去确定方法，在加载期间，
 * 确定调用那个重载方法（是根据参数类型，调用那个重载方法，不是根据对象类型，去调用对象的具体方法，这里是静态的）`。
 */
public class Demo5 {

    public void method(Grandpa g) {
        System.out.println("Grandpa");
    }

    public void method(Father f) {
        System.out.println("Father");
    }

    public void Son(Son s) {
        System.out.println("Son");
    }

    /*
    method方法是重载的，创建两个对象，静态类型是Grandpa，实际类型是Father和Son
    调用method方法，执行结果?
     */

    /**
     * 执行结果都是执行的Grandpa,根据上面的解释，这属于方法的静态分配，重载方法的分配，是根据变量的静态类型确定的，
     * 在编译期间那参数的静态类型去和方法中的参数类型进行匹配。
     *
     * 》》》 字节码层次的分析是，这个invokevirtual指令，直接获取传入参数的静态类型，然后去确定调用的那个具体的重载方法，然后指令
     *         在根据确定的方法，进行从低向上的实际对象的多态流程查找。
     */
    public static void main(String[] args) {
        Grandpa g1 = new Father();
        Grandpa g2 = new Son();
        Demo5 d = new Demo5();
        d.method(g1);
        d.method(g2);
        /*
        Grandpa
        Grandpa
         */
    }
    /**字节码
     *  0 new #7 <byteCode/Father>
     *  3 dup
     *  4 invokespecial #8 <byteCode/Father.<init>>
     *  7 astore_1
     *
     *  8 new #9 <byteCode/Son>
     * 11 dup
     * 12 invokespecial #10 <byteCode/Son.<init>>
     * 15 astore_2
     *
     * 16 new #11 <byteCode/Demo5>
     * 19 dup
     * 20 invokespecial #12 <byteCode/Demo5.<init>>
     * 23 astore_3
     *
     * //invokevirtual： 调用虚方法，运行期间动态查找的过程，用静态类型去判定
     * 24 aload_3
     * 25 aload_1
     * 26 invokevirtual #13 <byteCode/Demo5.method>
     *
     * 29 aload_3
     * 30 aload_2
     * 31 invokevirtual #13 <byteCode/Demo5.method>
     * 34 return
     */
}

class Grandpa {

}

class Father extends Grandpa {

}

class Son extends Father {

}