package interview.SerializableDemo;

import org.junit.Test;

import java.io.*;
import java.util.Date;

/**
 * 序列化和反序列化对象:
 * 步骤：序列化
 * 1.创建一个ObjectOutputStream输出流：
 * 2.调用ObjectOutputStream对象的writerObject输出可序列化对象
 * <p>
 * 反序列化：
 * 1.创建一个ObjectInputStream输入流
 * 2.调用ObjectInputStream对象的readObject得到序列化的对象。
 * <p>
 * <p>
 * 输出告诉我们，反序列化并不会调用构造方法。
 * 反序列的对象是由JVM自己生成的对象，不通过构造方法生成。
 *
 * @author: mahao
 * @date: 2019/8/24
 */
public class UserMain {

    public static void main(String[] args) {
        //序列化对象
        User user = new User("mahao", 18, new Date(), true);
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("user.obj"));
            out.writeObject(user);

            //反序列化
            in = new ObjectInputStream(new FileInputStream("user.obj"));
            User serUser = (User) in.readObject();
            System.out.println(user == serUser);
            System.out.println(serUser);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Test
    public void test2() throws Exception {
        //反序列化
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("user.obj"));
        User serUser = (User) in.readObject();
        // System.out.println(user == serUser);
        System.out.println(serUser);
    }
}
