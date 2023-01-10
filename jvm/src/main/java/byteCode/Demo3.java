package byteCode;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * 异常表的字节码分析:
 */
public class Demo3 {

    /**
     *  public int getbyId(int);
     *     descriptor: (I)I
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=3, locals=4, args_size=2
     *          0: new           #2                  // class java/net/ServerSocket   创建socket对象
     *          3: dup
     *          4: sipush        9999                 //数据押栈 9999
     *          7: invokespecial #3                  // Method java/net/ServerSocket."<init>":(I)V  执行构造方法
     *         10: astore_2                            //将创建的对象，引用到ss中
     *         11: aload_2
     *         12: invokevirtual #4                  // Method java/net/ServerSocket.accept:()Ljava/net/Socket;  执行方法
     *         15: pop
     *                  正常执行，则不会发生问题，执行完finally，退出
     *         16: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         19: bipush        123
     *         21: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V  //finally的语句
     *         24: goto          78                  //跳转到78行，结束语句
     *
     *
     *                              异常处理  FileNotFoundException
     *         27: astore_2
     *         28: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         31: bipush        123
     *         33: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
     *         36: goto          78
     *
     *                               IOException
     *         39: astore_2
     *         40: aload_2
     *         41: invokevirtual #9                  // Method java/io/IOException.printStackTrace:()V
     *         44: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         47: bipush        123
     *         49: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
     *         52: goto          78
     *
     *                              Exception
     *         55: astore_2
     *         56: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         59: bipush        123
     *         61: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
     *         64: goto          78
     *                                      any
     *         67: astore_3
     *         68: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         71: bipush        123
     *         73: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
     *         76: aload_3
     *         77: athrow
     *
     *         78: iload_1
     *         79: ireturn
     *       Exception table:
     *          from    to  target type
     *              0    16    27   Class java/io/FileNotFoundException
     *              0    16    39   Class java/io/IOException
     *              0    16    55   Class java/lang/Exception
     *              0    16    67   any
     */
    public int getbyId(int id) {
        try {
            ServerSocket ss = new ServerSocket(9999);
            ss.accept();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {

        } finally {
            System.out.println(123);
        }
        return id;
    }

    /**
     * public void getbyId2(int) throws java.io.FileNotFoundException, java.io.IOException, java.lang.Exception;
     *     descriptor: (I)V
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=0, locals=2, args_size=2
     *          0: return
     *       LineNumberTable:
     *         line 92: 0
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       1     0  this   LbyteCode/Demo3;
     *             0       1     1    id   I
     *     Exceptions://和code属性同级，表示的是方法的签名属性
     *       throws java.io.FileNotFoundException, java.io.IOException, java.lang.Exception
     */
    public void getbyId2(int id) throws FileNotFoundException,IOException,Exception {

    }
}
