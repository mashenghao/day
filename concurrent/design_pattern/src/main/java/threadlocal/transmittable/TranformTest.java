package threadlocal.transmittable;

import java.util.TimerTask;

/**
 * agent 测试使用：
 * 1. TimeTask 类，修改类TimeTask，在类中添加属性存储当前线程上下值，在run方法中
 * 添加 before  和 after方法代码片段。
 * <p>
 * 2.Executor方法是在 execute 和 submit的执行前添加对 runnable  和 callable的包装。
 * TtlRunnable.get(runnable);
 *
 * @author mahao
 * @date 2023/04/23
 */
public class TranformTest {


    static {
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                System.out.println(124);
            }
        };

        tt.run();
    }

    public static void main(String[] args) {

    }
}
