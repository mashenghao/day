package interview.single;

/**
 * 饿汉式
 * 使用静态内部类
 */
public class Singleton5 {

    private Singleton5() {

    }

    public static Singleton5 getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        public static Singleton5 instance = new Singleton5();
    }
}
