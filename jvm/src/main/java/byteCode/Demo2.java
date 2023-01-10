package byteCode;

import demo.AA;

/**
 * 复杂的字节码分析，构造方法的分析:
 *
 *参数
 *
 */
public class Demo2 implements AA {

    private int a = 1;

    public final int b = 10;

    String s = "属性";

    {
        a = 5;
    }

    public Demo2(int a1, String s1) {
        this.a = a;
        this.s = s;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }


    public void run() {
        String s2 = "你好";
        String s3 = "你好";
        System.out.println(s2 == s3);
    }

    public static void main(String[] args) {


    }
}
