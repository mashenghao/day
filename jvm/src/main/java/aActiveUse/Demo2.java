package aActiveUse;

import java.io.PrintStream;

/**
 * final关键字的使用，对类的初始化影响;包括助记符技术。
 * <p>
 * 问题2：
 * 被final修饰的静态变量，不是变量了，是常量，常量在编译阶段，会存入到调用方法的类
 * 的常量池中。本质上，调用类，并没有直接引用到定义常量的类，因此不会触发初始化。
 * 所以，对常量是在编译期间，直接拷贝到类的常量池中，使用常量，可以直接脱离常量class文件。
 * <p>
 * ldc是将int float 或是String类型的常量值从常量池汇中推送至栈顶
 * bipush是将byte类型常量推送到栈顶（-128-127）
 * sipush是将short常量值（-32768-32767）推送至栈顶
 * iconst_1表示将int类型1推送至栈顶。
 *
 *
 * <p>
 * 1.运行Parent2.p，父类静态变量p，不加final，初始化类是？
 * <p>
 * 2.运行Parent2.p，父类静态变量p，加final，初始化类是？
 */
public class Demo2 {
    public static void main(String[] args) {
        PrintStream p = System.out;
        p.println(Parent2.p);
        p.println(Parent2.s);
        p.println(Parent2.i);
        p.println(Parent2.m);
    }
}

class Parent2 {

    public final static String p = "parent";

    public final static byte s = 127;

    public final static short i = 128;

    public final static int m = 1;

    static {
        System.out.println("parent 被执行");
    }
}


