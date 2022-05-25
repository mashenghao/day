package interview.enmuType;


import java.util.ArrayList;
import java.util.List;

/**
 * 枚举类型：
 * 枚举类型，是用于表示情况是确定的事物，将所有
 * 可能出现的情况，全部列举出来。
 *
 * <p>
 * 自定义枚举类的实现，表示四季
 */
public class Enum1 {

    //1.私有化构造方法，不予许外部创建事物
    private Enum1(String key, String val) {
        this.key = key;
        this.val = val;

    }

    //3.指定对象的属性
    public final String key;
    public final String val;


    //2.定义所有的事物,并初始化
    public static final Enum1 SPRING = new Enum1("春季", "春天来了");
    public static final Enum1 SUMMARY = new Enum1("夏季", "夏天来了");
    public static final Enum1 AUTUMN = new Enum1("秋季", "秋天来了");
    public static final Enum1 WINTER = new Enum1("冬季", "冬天来了");

    @Override
    public String toString() {
        return "Enum1{" +
                "key='" + key + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}

class TestEnum1 {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User("张三", Enum1.AUTUMN));
        list.add(new User("李四", Enum1.SPRING));
        list.add(new User("王五", Enum1.WINTER));
        User user = list.get(1);

        if (user.status == Enum1.SPRING) {//直接==比较，因为只有一个实例，是单例静态的
            System.out.println(user.status.val);
        }

    }
}

//封装个用户
class User {

    String name;
    Enum1 status;

    public User(String name, Enum1 status) {
        this.name = name;
        this.status = status;
    }
}