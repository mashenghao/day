package interview;

/**
 * 接口的问题:
 *
 * @author: mahao
 * @date: 2019/8/22
 */
public class CInterface {
}


/**
 * 定义一个接口AA ，一个类AAImpl实现接口
 */
interface AA {

    /*
    接口中定义的属性，都是被public static final修饰的，默认大写;
    必须被属性给定初始值，不允许存在静态代码块
     */
    int NUM = 10;

    //普通方法
    void method1() throws AAException;

    /*
    创建默认的接口中的方法，可以实现接口,接口中的所有方法都默认是由public abstract修饰的。
     */
    default void method2() {
        System.out.println("接口中可以创建默认的实现方法");
    }

    /**
     * 接口中允许定义内部类和内部接口（Map），修饰符是public static静态内部类，只是表示一种从属关系，和外部内没关系。
     */
    public static class AAException extends Exception {
        public AAException(String msg) {
            super(msg);
        }
    }
}


class AAImpl implements AA {

    public static void main(String[] args) {
        int b = NUM;
    }

    @Override
    public void method1() throws AAException {

    }
}