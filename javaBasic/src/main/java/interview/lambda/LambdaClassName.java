package interview.lambda;

/**
 * Lambda 自动生成的类名：
 * 内部类和 Lambda 表达式的命名规则
 * 1) 成员内部类，包括普通成员内部类、静态成员内部类，外部类名$内部类名
 * 2) 局部内部类，外部类名$n内部类名，n 从 1 开始，每个函数都有不同的 n 值
 * 3) 匿名内部类，外部类名$n，n 从 1 开始
 * 4) Lambda 表达式类，外部类名$$Lambda$n，n 从 1 开始。
 * ————————————————
 * 版权声明：本文为CSDN博主「啊大1号」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/a3192048/article/details/104162299
 *
 * @author mahao
 * @date 2022/11/22
 */
public class LambdaClassName {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println(124);
        };

//        class interview.lambda.LambdaClassName$$Lambda$1/81628611
        //$$Lambda$X 表示是内部lamda的第几个
        System.out.println(runnable.getClass());
    }
}
