package basic.day08_duotai.jvm;

/**
 * 字段不参与多态：
 * 多态是指令invokevirtual的特性，对虚方法执行根据实际类型去定位调用的方法。
 *
 * @author mahao
 * @date 2022/03/26
 */
public class Main {

    static class Father {
        int i;

        Father() {
            i = -1;
            showI();
        }

        private void showI() {
            System.out.println("father: " + i);
        }
    }

    static class Son extends Father {
        int i;

        Son() {
            i = 1;
            showI();
        }

        private void showI() {
            System.out.println("son: " + i);
        }
    }

    public static void main(String[] args) {
        //1. 当重写了showI方法，父类和子类的构造函数中都调用的是子类的函数。
        //因为是invokevirtual指令，都会根据实际类型去调用方法。 这个调用的主题都是子类的对象。
        Father f = new Son();
        Son s = (Son) f;
        System.out.println(f.i + " -- " + s.i);


        //将showI改为私有方法后,就是调用的各自的方法，用的是invokeSpecial。
//        father: -1
//        son: 1
//        -1 -- 1
    }
}
