package interview.FCallByValue;

/**
 * 证明java中只有按值传递，没有按引用传递，方法修改只会修改变量的拷贝值，
 * 不会影响变量的实际值。
 *
 * 在swap方法中，a、b的值进行交换，并不会影响到 num1、num2。
 * 因为，a、b中的值，只是从 num1、num2 的复制过来的。
 * 也就是说，a、b相当于num1、num2 的副本，副本的内容无论怎么修改，都不会影响到原件本身。
 *
 * @author: mahao
 * @date: 2019/8/22
 */
public class Example1 {

    public static void main(String[] args) {
        int a = 1, b = 2;
        swap(a, b);
        System.out.println("mian a : " + a);
        System.out.println("main b : " + b);

    }

    private static void swap(int a, int b) {
        int t = a;
        a = b;
        b = t;
        System.out.println("swap a : " + a);
        System.out.println("swap b : " + b);
    }
}
