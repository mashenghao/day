package aActiveUse;

/**
 * 1. b放在位置1，结果是什么？
 * <p>
 * 2. b放在位置2，结果是什么？
 */
public class Demo6 {

    //tried to access class aActiveUse.Singleton from class aActiveUse.Demo6会有这个错误，Singleton在启动类加载器已经加载了。
    public static void main(String[] args) {
        Singleton  s = Singleton .getInstance();
        System.out.println(Singleton.a);
        System.out.println(Singleton.b);


    }
}

/**
 * 初始化顺序，对于静态变量，从上到下的顺序进行初始化
 */
class Singleton {

    //连接-准备： 为静态变量分配内存，并默认值。a=0，instance=null，b=0；

    //初始化，按照从上向下的顺序。


    //a没有赋值，默认为0
    public static int a;

    //位置1
    // public static int b = 0;

    //给静态变量初始化，则a=1，b=1;
    private static Singleton instance = new Singleton();

    private Singleton() {
        a++;
        b++;
    }

    //给变量b，初始化，b=0覆盖b1；
    //位置2
    public static int b = 0;

    public static Singleton getInstance() {
        return instance;
    }
}