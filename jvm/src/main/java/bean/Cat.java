package bean;

/**
 * @program: jvn
 * @Date: 2019/7/9 20:09
 * @Author: mahao
 * @Description:
 */
public class Cat {

    public Cat() {
        System.out.println("Cat 被加载由" + Cat.class.getClassLoader());

        //System.out.println("from cat  访问 Person 的calss" + Person.class);
    }

    static {
        //System.out.println("Cat init");
    }
}
