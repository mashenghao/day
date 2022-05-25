package interview.enmuType;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举类型
 */
public enum Enum2 {


    SPRING("春季", "春天来了"),

    SUMMARY("夏季", "夏天来了"),

    AUTUMN("秋季", "秋天来了"),

    WINTER("冬季", "冬天来了");


    public final String key;
    public final String val;

    private Enum2(String key, String val) {
        this.key = key;
        this.val = val;
    }


}

class TestEnum2 {

    public static void main(String[] args) {
        List<User2> list = new ArrayList<>();
        list.add(new User2("张三", Enum2.AUTUMN));
        list.add(new User2("李四", Enum2.SPRING));
        list.add(new User2("王五", Enum2.WINTER));
        User2 user = list.get(1);

        switch (user.status) {
            case AUTUMN:
                System.out.println(Enum2.AUTUMN);
                break;
            case SUMMARY:
                System.out.println(Enum2.SUMMARY);
                break;
            case SPRING:
                System.out.println(Enum2.SPRING);
                break;
            case WINTER:
                System.out.println(Enum2.WINTER);
                break;
        }
    }
}

class User2 {

    String name;
    Enum2 status;

    public User2(String name, Enum2 status) {
        this.name = name;
        this.status = status;
    }
}