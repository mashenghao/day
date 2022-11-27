package jReactor.oneReacotr;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * * 单reactor模式，只有一个reactor负责所有事件的分发。
 * * 每个连接的处理器都存放在了SelectionKey中了。
 * * 1. 负责接收客户端的连接
 * * 2. 负责事件绑定，为每个新来的连接，绑定handler。
 *
 * @author mahao
 * @date 2022/11/20
 */
public class Reactor implements Runnable {

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    public Reactor() throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        //将当前serversocketchannel注册到selector中，表示要selector对accepted事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //同时，为这个服务channel添加事件处理器，服务channel的作用就是为所有的accepted事件得到的连接
        //添加事件处理器。
        selectionKey.attach(new Acceptor(serverSocketChannel, selector));

        // 这里需要唤醒selector，因为Reactor 线程已经阻塞在 select() 方法了，
        // 此时该通道感兴趣的事件可能不是 OP_READ是0，这里将通道感兴趣的事件改为 OP_READ，如果不唤醒的话，就只能在
        // 下次select 返回时才能有响应了，当然了也可以在 select 方法上设置超时
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            // 负责监听selector出现事件的channel，然后将channel对应的事件进行分发处理。
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    dispatch(key);
                }

                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        //获取到每个channel上的处理器 调用执行
        Runnable handler = (Runnable) selectionKey.attachment();
        //1. 当selectkey是serversocketchannel， 将会调动Acceptor,为新的socketChannel
        //添加处理器。
        //2. 如果是socketChannel，将会执行业务逻辑。
        handler.run();
    }
}
