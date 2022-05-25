package interview.SerializableDemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 验证ArrayList的序列化机制：
 * <p>
 * 省略了其他成员变量，从上面的代码中可以知道`ArrayList`实现了`java.io.Serializable`接口，
 * 那么我们就可以对它进行序列化及反序列化。因为`elementData`是`transient`的，
 * 所以我们认为这个成员变量不会被序列化而保留下来。我们写一个Demo，验证一下我们的想法：
 *
 * @author: mahao
 * @date: 2019/8/24
 */
public class MainClass4 {


    public static void main(String[] args) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        System.out.println("serializable  before : " + list);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("MainClass4.obj"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MainClass4.obj"));
        oos.writeObject(list);
        ArrayList<String> list2 = (ArrayList<String>) ois.readObject();
        System.out.println(list2);

        oos.close();
        ois.close();
    }
}
