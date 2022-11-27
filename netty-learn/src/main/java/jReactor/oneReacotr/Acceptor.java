package jReactor.oneReacotr;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 负责处理servercocketchannel的accept事件
 * <p>
 * 为新来的连接都添加上事件处理器。
 *
 * @author mahao
 * @date 2022/11/20
 */
public class Acceptor implements Runnable {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public Acceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }


    @Override
    public void run() {
        try {
            SocketChannel socket = serverSocketChannel.accept();
            //handler构造函数，为这个scoket添加处理器。
            new Handler(socket,selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
