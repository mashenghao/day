package basic.source.AHashMap;

/**
 * @program: javaSe
 * @Date: 2019/6/23 17:19
 * @Author: mahao
 * @Description:
 */
public class HashCodeDemo {

    public static void main(String[] args) {
        String a = "123";
        System.out.println(a.hashCode());
        System.out.println("123".hashCode());
        System.out.println(new String("123").hashCode());
        User u = new User();
        u.name = 1 ;
        User u2 = new User();
        u2.name=2;
        User u3 = new User();
        u3.name=1;
        System.out.println(u.hashCode());
        System.out.println(u2.hashCode());
        System.out.println(u3.hashCode());
    }

    static class User {
        int name;
    }
}
