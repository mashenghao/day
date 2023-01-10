package aActiveUse;

import java.util.Random;

/**
 * 当一个接口被初始化时，并不要求父接口初始化;
 * <p>
 * 子接口的常量只有在运行期间才能确定的，并不会放到调用类的常量池当中，这时会触发父接口的初始化。
 * <p>
 * https://blog.csdn.net/wzq6578702/article/details/79382182
 */
public class Demo5 {

    public static void main(String[] args) {
        System.out.println(Child5.b);
    }
}

interface Parent5 {
   // int a = new Date().getSeconds();
}

class Child5 implements Parent5 {
    public static int b = new Random().nextInt();

    static {
        System.out.println("child 被初始化");
    }
}