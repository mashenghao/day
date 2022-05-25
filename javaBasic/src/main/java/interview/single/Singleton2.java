package interview.single;

import java.io.IOException;
import java.util.Properties;

/**
 * 饿汉式
 * 静态代码块饿汉式，适合复杂的初始化对象
 */
public class Singleton2 {

    public static final Singleton2 instance;

    static {
        //从文件中，加载信息
        //加载类路径下的properties文件
        Properties ps = new Properties();
        try {
            ps.load(Singleton2.class.getClassLoader().getResourceAsStream("Singleton.properties"));
            String name = ps.getProperty("name");
            System.out.println(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance = new Singleton2();
    }


    private Singleton2() {
    }
}

class Singleton2Test {
    public static void main(String[] args) {
        Singleton2 s = Singleton2.instance;
    }
}