package demo;

/**
 * @program: jvn
 * @Date: 2019/7/11 12:30
 * @Author: mahao
 * @Description:
 */
public class AAImol implements AA {

    @Override
    public void run() {
        System.out.println("我是AAImol" + this.getClass().getClassLoader());
    }
}
