package interview.SerializableDemo;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建可以被实例化的类
 *
 * @author: mahao
 * @date: 2019/8/24
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8683452581122892180L;

    private String name;
    private int age;
    private Date time;
    private boolean isMan;
    //static String flag = "static field";

    public User(String name, int age, Date time, boolean isMan) {
        System.out.println("init method be invoke ... ");
        this.name = name;
        this.age = age;
        this.time = time;
        this.isMan = isMan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", time=" + time +
                ", isMan=" + isMan +
                '}';
    }
}
