package bean;

/**
 * @program: jvn
 * @Date: 2019/7/9 20:09
 * @Author: mahao
 * @Description:
 */
public class Person {


    public Person() {
        System.out.println("Person 被加载由" + Person.class.getClassLoader());

        System.out.println("在person中访问Cat" + Cat.class);
        new Cat();

    }

    static {
        //   System.out.println("Person 初始化");
    }
}
