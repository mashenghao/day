package jReactor.multiReactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

/**
 * 主reactor，还是负责连接的接收， 同时将各个channel注册到子
 * reactor上的selector上。
 *
 * @author mahao
 * @date 2022/11/20
 */
@Slf4j
public class MainReactor implements Runnable {

    SubReactors subReactors;
    int inx = 0;
    ServerSocketChannel serverSocketChannel;
    //业务线程池
    ExecutorService businessPool;

    /**
     * 外部会将子reactor上的selector传入到mainreactor的构造函数中，
     * mainreactor为这些子reacotr注册上客户端连接。
     *
     * @param subReactors
     * @param businessPool
     */
    public MainReactor(SubReactors subReactors, ExecutorService businessPool) {
        this.subReactors = subReactors;
        this.businessPool = businessPool;
    }


    //只需要负责监听就可以了
    @Override
    public void run() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9999));
            Acceptor acceptor = new Acceptor();
            while (!Thread.interrupted()) {
                //注册
                acceptor.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                log.info("mianReactor 请求等待接收");
                SocketChannel socketChannel = serverSocketChannel.accept();
                log.info("mianreactor 接收到新客户端连接 [{}]", socketChannel.toString());
                //轮着注册
                new HandlerMultiThread(subReactors.subReactors[(inx++ % subReactors.num)], socketChannel, businessPool);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
