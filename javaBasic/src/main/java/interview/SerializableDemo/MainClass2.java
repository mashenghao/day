package interview.SerializableDemo;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *属性中的引用类型也必须是Serializable的子类
 *
 * @author: mahao
 * @date: 2019/8/24
 */
public class MainClass2 {
    /*
    Exception in thread "main" java.io.NotSerializableException: interview.SerializableDemo.Dog

     */
    public static void main(String[] args) throws Exception {
        Person p = new Person();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.obj"));
        oos.writeObject(p);
    }

    
}

class Person implements Serializable {
    private String name;
    private Dog dog = new Dog();
}

class Dog {

}