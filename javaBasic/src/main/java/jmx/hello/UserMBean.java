package jmx.hello;

/**
 * 实体类需要继承 MBean 接口类，而接口类的命名规则是 「实体类名+MBean」,这是固定的规则。
 *
 * @author mash
 * @date 2023/9/3 14:55
 */
public interface UserMBean {

    String getName();

    void setName(String name);

    String getPhone();

    void say();

}
