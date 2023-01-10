package interview.ObjectDeath;

/**
 * 通过引用计数法，无法解决对象之间循环依赖的问题。
 *
 * @author: mahao
 * @date: 2019/10/13
 */
public class ObjectDeath1 {

    public static void main(String[] args) {

        ClassA classA = new ClassA();
        ClassB classB = new ClassB();
        classA.classB = classB;//B中引用A
        classB.classA = classB;//A中引用B
        /*
           到现在为止：
           ClassA的对象的引用数为2，分别是calssA，classB.classA
           ClassB的对象的引用数为2，分别是classB，classA.classB
         */
        classA = null;//classA置为null，则ClassA对象引用减1
        classB = null;//classB置为null，则ClassB对象引用减1

        /*
        但是，此时，ClassA对象的计数器为1，ClassB对象的计数器为2，
        并且这两个对象已经已经无法可以访问，所以发生内存泄漏了。
         */

    }

    static class ClassA {
        ClassB classB;
    }

    static class ClassB {
        ClassB classA;
    }
}
