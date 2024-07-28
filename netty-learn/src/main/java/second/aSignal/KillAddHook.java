package second.aSignal;

import util.T;

/**
 * RunningTime带的处理回调函数:
 * 被两个地方调用，1是 系统级别的kill， 另一个是内部system.exit.
 *
 * SignalHandler是不会被System.exit调用的。
 *
 * @author mash
 * @date 2024/7/6 下午5:08
 */
public class KillAddHook {

    public static void main(String[] args) {

        Thread thread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                T.print("被执行了");
                thread.interrupt();
            }
        });

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
