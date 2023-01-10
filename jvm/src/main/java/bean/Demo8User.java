package bean;

/**
 * @program: jvn
 * @Date: 2019/7/10 9:27
 * @Author: mahao
 * @Description:
 */
public class Demo8User {

    private Demo8User user;

    public void setUser(Demo8User demo8User) {
        this.user = demo8User;
        System.out.println("111111111111111");
    }

    public void setUser(Object obj) {
        this.user = (Demo8User) obj;
        System.out.println("222222222222");

    }
}
