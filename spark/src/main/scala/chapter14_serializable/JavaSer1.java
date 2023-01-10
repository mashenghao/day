package chapter14_serializable;

import java.io.*;

/**
 * @author: mahao
 * @date: 2021/12/11
 */
public class JavaSer1 implements Serializable {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //1、java序列化的方式，最基础的就是这个，使用ObjectOutputStream。
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
        outputStream.writeObject(new IllegalArgumentException("我是非法参数异常"));
        byte[] bytes = out.toByteArray();
        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Object object = inputStream.readObject();
        System.out.println(object);

        //2.java也支持自定义序列化方式，通过实现方法。readObject和writeObject这两个方法。
        User user = new User();
        user.setName("zs");
        user.setAge(10);
        new ObjectOutputStream(out).writeObject(user);
        Object readObject = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray())).readObject();
        System.out.println(readObject);
    }

    public static class User implements Serializable {
        private String name;
        private Integer age;

        public User() {
        }

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        private void writeObject(ObjectOutputStream out)
                throws IOException {
            out.writeObject(name);
            out.writeObject(age);
            System.out.println("调用序列化方法");
        }

        private void readObject(ObjectInputStream in)
                throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            age = (int) in.readObject();
            System.out.println("调用反序列化方法");
        }
    }
}

