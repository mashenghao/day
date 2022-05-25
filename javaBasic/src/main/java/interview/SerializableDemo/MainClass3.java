package interview.SerializableDemo;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 同一个对象序列化多次，会将这个对象序列化多次吗；
 *
 * @author: mahao
 * @date: 2019/8/24
 */
public class MainClass3 {

    public static void main(String[] args) throws Exception {
        User u1 = new User("mahao", 18, new Date(), true);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("MainClass3.obj"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MainClass3.obj"));
        oos.writeObject(u1);
        oos.writeObject(u1);
        User u2 = (User) ois.readObject();
        User u3 = (User) ois.readObject();
        System.out.println(u1 == u2);//false u2是虚拟机新生成的对象，和u1不是同一个
        System.out.println(u2 == u3);//true  u3和u2是同一对象，u2 u3是同一个对象序列化而来的，不会将这个对象序列化多次。

        /*
        结论:
        从输出结果可以看出，Java序列化同一对象，并不会将此对象序列化多次得到多个对象。序列化时，如果已经
        序列化过某个对象，则第二次序列化时，不会对他进行多次序列化，只会输出编号。

        Java序列化算法：
        1.所有保存到磁盘的对象都有一个序列化编码号

        2.当程序试图序列化一个对象时，会先检查此对象是否已经序列化过，只有对象从未在此虚拟机上序列化过，
        才会将对象对象序列化为字节序列输出；

        3.如果已经序列化过了，则会直接输出编号即可。

         */

    }


    /*
    java序列化算法也会带来问题，他不会重复序列化一个已经序列化过的对象，只会记录那个序列化后的对象的编号。
    如果序列化一个可变对象，更改了对象的内容，再次序列化，则不会再次序列化，而是只保存上次的编号。
     */
    @Test
    public void testTrouble() throws Exception {
        User u1 = new User("mahao", 18, new Date(), true);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("MainClass3tr.obj"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MainClass3tr.obj"));
        oos.writeObject(u1);
        u1.setName("ABC");
        oos.writeObject(u1);
        User u2 = (User) ois.readObject();
        User u3 = (User) ois.readObject();
        System.out.println(u2);
        System.out.println(u3);
    }

}
