package interview.single;

/**
 * 单例设计模式：
 * <p>
 * 饿汉式：直接实例化饿汉式
 */
public class Singleton1 {

    //自己创建实例对象，声明为public和final，不可变，直接暴露出来
    public static final Singleton1 instance = new Singleton1();

    //构造方法私有化
    private Singleton1() {

    }

}

class Singleton1Test {
    public static void main(String[] args) {
        Singleton1 s = Singleton1.instance;
    }
}