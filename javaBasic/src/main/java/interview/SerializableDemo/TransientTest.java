package interview.SerializableDemo;

import java.io.*;
import java.util.Date;

/**
 * transient关键字的使用：
 * <p>
 * 些时候，我们有这样的需求，某些属性不需要序列化。
 * 使用transient关键字选择不需要序列化的字段。
 * <p>
 * 从输出我们看到，使用transient修饰的属性，java序列化时，
 * 会忽略掉此字段，所以反序列化出的对象，被transient修饰的属性是默认值。
 * 对于引用类型，值是null；基本类型，值是0；boolean类型，值是false。
 *
 * @author: mahao
 * @date: 2019/8/24
 */
public class TransientTest {


    public static void main(String[] args) throws Exception {
        User u1 = new User("mahao", 18, new Date(), true);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("TransientTest.obj"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("TransientTest.obj"));
        oos.writeObject(u1);
        User u2 = (User) ois.readObject();
        System.out.println(u1);
        System.out.println(u2);

    }


    static class User implements Serializable {

        private String name;
        private int age;
        private Date time;
        private transient boolean isMan;
        private transient Dog dog = new Dog();

        public User(String name, int age, Date time, boolean isMan) {
            System.out.println("init method be invoke ... ");
            this.name = name;
            this.age = age;
            this.time = time;
            this.isMan = isMan;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", time=" + time +
                    ", isMan=" + isMan +
                    ", dog=" + dog +
                    '}';
        }
    }

}
