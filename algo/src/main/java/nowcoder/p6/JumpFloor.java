package nowcoder.p6;

/**
 * 链接：https://www.nowcoder.com/questionTerminal/8c82a5b80378478f9484d87d1c5f12a4?toCommentId=3125967
 * 来源：牛客网
 * <p>
 * 直接用递归出来结果，简单粗暴,分析一下这种解法，初次一看，使用递归的解法很简洁，但是却有很多问题，
 * <p>
 * 调用太多次自身函数，每次函数的调用，都需要在内存栈中分配空间已保存参数、返回地址、变量，同时需要把数据压入栈中和弹出栈中都需要消费时间
 * <p>
 * 容易栈内存溢出，调用太深，容易溢出
 * <p>
 * 带来重复计算问题，比如求f(10)的值，
 * 则需要f(10) = f(9) + f(8); 但再求f(9)时，有需要f(8) + f(7) ,在求f(10)时候，f(8)已经计算过了，导致了重复计算，如果用树表示，则会看着更加清晰：
 */
public class JumpFloor {

    //1 2 3 5 8
    public int jumpFloor(int target) {
        int a = 1, b = 0;//a是记录前一个（targrt-1项）的数据，b是要求的target项结果
        while (target-- >= 0) {
            b = a + b; //在新的一循环中，b是前一项 ，a 是前二项 ， 等号左面的是最新 b ，所以有这个等式
            a = b - a; //求出最新的b后，a应该赋值为前一项，就是把以前老的b的数据复制过来，因为b已经更新，
            //b-a 就是求出以前的b值
        }
        return b;
    }

    /**
     * 剑指offer给出的官方解答
     *
     * @param target
     * @return
     */
    public int jumpFloor2(int target) {
        if (target < 3) {
            return target;
        }
        int fn = 0, f1 = 2, f2 = 1;
        for (int i = 3; i <= target; i++) {

            fn = f1 + f2;

            f2 = f1;
            f1 = fn;
        }
        return fn;
    }

    public static void main(String[] args) {
        JumpFloor j = new JumpFloor();
        int a = j.jumpFloor(1);
        int b = j.jumpFloor2(1);
        System.out.println(a + "-----" + b);
    }
}
