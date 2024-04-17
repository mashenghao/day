package jmx.hello;

/**
 * 如果想要暴露User的一些获取和设置的方法，必须先创建一个接口，接口中声明暴露的参数。
 *
 * @author mash
 * @date 2023/9/3 14:56
 */
public class User implements UserMBean {

    private String name;
    private String phone;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void say() {
        System.out.println("say 被调用 ------- " + Thread.currentThread().getName());
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
