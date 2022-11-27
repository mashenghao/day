package jReactor.multiReactor;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author mahao
 * @date 2022/11/22
 */
public class Server {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        ServerBootStrap serverBootStrap = new ServerBootStrap();
        Future<Void> future = serverBootStrap.bind(9999);

        future.get();

        System.out.println(" ========= 任务结束 ========");
    }
}
