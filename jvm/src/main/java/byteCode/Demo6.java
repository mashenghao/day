package byteCode;

/**
 * 方法重写与invokeVirtual字节码指令的关系：
 * <p>
 * 方法的动态分配：
 * <p>
 * 方法的动态分派涉及到一个重要概念：方法的接收者。
 * <p>
 * invokevirtual字节码指令多态查找流程：
 * 1.去操作数栈的栈顶，找到到栈顶的元素，然后获取栈顶元素的实际指向对象的实际类型
 * 2.根据实际对象的类型，在这个类型中会去寻找invokevirtual参数中的方法（方法名称和描述符一样），如果权限通过，则直接返回这个
 *  对象的类型的方法的引用。
 * 3.如果实际对象类型中找寻不到，则会根据当前实际对象继承体系去向上寻找，寻找父类中是否有方法，查找到，则返回方法
 *  引用，否则抛出异常；
 *
 *  可以看到下面字节码第17行和21行，他们有相同的符号引用，在运行期间，被解析到不同的直接引用上面了，
 *
 *
 *  重点：
 *  方法重载和方法重写的区别：
 *
 *   1.调用方：
 *      重载对方法调用是同一个对象纠结调用那一个方法，是同一个对象中，确定调用哪一个重载方法。
 *      重写是纠结哪一个对象去调用这个方法，是有一个方法，确定是那个对象来调用了。
 *   2.执行顺序：
 *      重载是在编译期间确定使用哪一个具体的方法（确定方法描述符），是先于重写的，他不需要运行就可以确定，是根据参数的静态类型。
 *      重写则是在运行期间确定这个方法具体由那个对象调用，是有对象本身还是有父类对象，会根据多态查找流程去确定调用对象。
 *   联系：
 *      方法的重载执行先于重写，先通过重载，去确定执行的方法的名称，描述符，访问权限等方法标识，然后通过这个确定的方法，去寻找
 *      具体的执行对象。
 *
 */
public class Demo6 {

    /**
     * 0 new #2 <byteCode/Apple>  //1.分配内存空间
     * 3 dup   //压栈
     * 4 invokespecial #3 <byteCode/Apple.<init>>  //2.调用构造方法
     * 7 astore_1  //3.存储对象的引用变量
     * <p>
     * 8 new #4 <byteCode/Bear>
     * 11 dup
     * 12 invokespecial #5 <byteCode/Bear.<init>>
     * 15 astore_2
     * <p>
     * 16 aload_1
     * 17 invokevirtual #6 <byteCode/Fruit.test>
     * 20 aload_2
     * 21 invokevirtual #6 <byteCode/Fruit.test>
     * 24 return
     */
    public static void main(String[] args) {
        Fruit f1 = new Apple();
        Fruit f2 = new Bear();
        f1.test();
        f2.test();
        /*
        Apple
        Bear
         */
    }
}

class Fruit {

    public void test() {
        System.out.println("Fruit");
    }
}

class Apple extends Fruit {

    public void test() {
        System.out.println("Apple");
    }
}

class Bear extends Fruit {

    public void test() {
        System.out.println("Bear");
    }
}