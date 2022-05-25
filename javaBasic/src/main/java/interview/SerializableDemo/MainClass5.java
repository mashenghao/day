package interview.SerializableDemo;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义实现序列化和反序列化策略
 *
 * @author: mahao
 * @date: 2019/8/24
 */
public class MainClass5 {

    public static void main(String[] args) throws Exception {
        Bean.Dog d1 = new Bean.Dog("d1");
        Bean.Dog d2 = new Bean.Dog("d2");
        Bean.Dog d3 = new Bean.Dog("d3");

        Bean bean = new Bean("mahao", 10.5f, new Bean.Dog[]{d1, d2, d3},
                Arrays.asList("aa", "bb", "cc"));

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("MainClass5.obj"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MainClass5.obj"));
        oos.writeObject(bean);

        Bean bean1 = (Bean) ois.readObject();

        System.out.println(bean);
        System.out.println(bean1);

    }

}

//transient加上则属性就不会去序列化了，但是其他的属性仍然会序列化，
//所以transient的使用时，对某些需要自定义序列化的属性，进行加上该关键字，然后在
//writeObject中实现该属性的自定义序列化实现，并不是，自定义了实现，该类就不执行默认的
//序列化了。
class Bean implements Serializable {

    private String name;

    private float sum;

    private Dog[] dogs;

    private transient List<String> list;

    public Bean(String name, float sum, Dog[] dogs, List<String> list) {
        this.name = name;
        this.sum = sum;
        this.dogs = dogs;
        this.list = list;
    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        System.out.println("writeObject be invoke");
        s.defaultWriteObject();

        s.writeObject(name);//写入名字
        //忽略sum
        //写入数组大小
        s.writeInt(dogs.length);
        for (Dog d : dogs) {//写入数组
            s.writeObject(d);
        }

        s.writeObject(list);//list直接调用他的自定义实现

    }

    //自定义读取
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        System.out.println("readObject be invoke ");
        s.defaultReadObject();
        name = (String) s.readObject();
        int length = s.readInt();
        dogs = new Dog[length];
        for (int i = 0; i < length - 1; i++) {
            dogs[i] = (Dog) s.readObject();
        }
        s.readObject();
        list = (List<String>) s.readObject();
    }


    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", sum=" + sum +
                ", dogs=" + Arrays.toString(dogs) +
                ", list=" + list +
                '}';
    }

    static class Dog implements Serializable {
        String name;

        public Dog(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}