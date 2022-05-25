package interview.threadCreate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 介绍线程创建的四种方式：
 */
public class MainClass {
}

/**
 * 创建方式三： 实现Callanle接口
 *
 */
class CallableThread implements Callable<String> {

    @Override
    public String call() throws Exception {

        String str= "123456" ;
        System.out.println("-----");
        Thread.sleep(3000);
        return str;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CallableThread callableThread = new CallableThread();
        FutureTask<String> futureTask = new FutureTask<>(callableThread);
        Thread thread = new Thread(futureTask);
        thread.start();
        String s = futureTask.get();
        System.out.println(s);
    }
}
