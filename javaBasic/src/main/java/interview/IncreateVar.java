package interview;

/**
 * 自增变量，i++ 和 ++i ,以及 i= i++赋值问题
 */
public class IncreateVar {

    /**
     * 执行过程尚未清楚，唯一迷惑的是 i=i++;
     * <p>
     * i = i++;
     * <p>
     * i初始值是1，i++操作是先取值，在自增，等式运算，是先计算右边的，最后是左边的。
     * 所有，过程如下，
     * 1.获取到i的初始值1， 然后记录这个数据
     * 2. i的值递增，进行i++操作，i变成了2
     * 3.最后赋值变量，将i的初始值，给等式左边，i=1；
     *
     * @param args
     */
    public static void main(String[] args) {
        int i = 1;
        i = i++;
        int j = i++;

        int k = i + ++i * i++;
        System.out.println("i = " + i);
        System.out.println("j = " + j);
        System.out.println("k = " + k);

    }
    //根据字节码，可以查看到执行的过程。
    /*
         0: iconst_1   //int i=1;
         1: istore_1

         2: iload_1
         3: iinc          1, 1  // i = i++； 将i从局部变量中取出放在操作数栈中，然后进行自增操作
         6: istore_1

         7: iload_1
         8: iinc          1, 1  //int j = i++;
        11: istore_2

        12: iload_1
        13: iinc          1, 1
        16: iload_1
        17: iload_1
        18: iinc          1, 1
        21: imul
        22: iadd
        23: istore_3


        24: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        27: new           #3                  // class java/lang/StringBuilder
        30: dup
        31: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        34: ldc           #5                  // String i =
        36: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        39: iload_1
        40: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        43: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        46: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        49: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        52: new           #3                  // class java/lang/StringBuilder
        55: dup
        56: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        59: ldc           #10                 // String j =
        61: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        64: iload_2
        65: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        68: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        71: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        74: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        77: new           #3                  // class java/lang/StringBuilder
        80: dup
        81: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        84: ldc           #11                 // String k =
        86: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        89: iload_3
        90: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        93: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        96: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        99: return

     */
}
