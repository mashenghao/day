package second.aSignal;

import sun.misc.Signal;
import sun.misc.SignalHandler;
import util.T;

/**
 * java 检测内核的信号状态，并做出对应处理:
 *
 * [SIGINT handler] : SIGINT
 * [SIGINT handler] : 要发起中断了，main主线程应该会被中断停了
 * [main] : 被中断了
 *
 *
 *总结：
 * 1. java可以通过SignalHandler注册信号，包括kill的TERM终止信号，但是注册之后，jvm注册的信号就会被覆盖掉，推荐用addHook方法。
 *  可以对操作系统的进行回调，也可以对System.exit进程内部终止函数拦截
 * 2.
 *
 * @author mash
 * @date 2024/7/6 下午4:48
 */
public class SignalRegister {

    public static void main(String[] args) {

        Thread mainThread = Thread.currentThread();


        //注册SIGINT (2): 用户中断请求（通常是由Ctrl+C触发）。
        Signal signal = new Signal("INT");
        SignalHandler signalHandler = new SignalHandler() {
            @Override
            public void handle(Signal sig) {
                T.print(sig);
                //.....
                T.sleep(10_000);

                T.print("要发起中断了，main主线程应该会被中断停了");
                mainThread.interrupt();
            }
        };

        Signal.handle(signal, signalHandler);


        /**
         * java.lang.Shutdown#shutdown() 是拦截操作系统的系统
         */
        Signal signalTERM = new Signal("TERM");
        SignalHandler signalHandlerTERM = new SignalHandler() {
            @Override
            public void handle(Signal sig) {
                T.print(sig);
                //.....
                T.sleep(10_000);

                //需要自己去做终止任务， System.exit就是jvm的终止注册，如果我们注册了，jvm就不会自己去停止了。
                System.exit(0);
            }
        };

        signalTERM.handle(signalTERM, signalHandlerTERM);


        // 主线程将无限期地等待信号
        while (true) {
            try {
                Thread.sleep(3000);
//                System.exit(0); 不会被信号处理器检测到，因为不是操作系统发起的。
            } catch (InterruptedException e) {
                T.print("被中断了");
                Thread.currentThread().interrupt();
                break;
            }
        }

    }
}
