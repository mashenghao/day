package jReactor.multiReactor;


import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;

/**
 * netty的分发器:
 *
 * 每一个 SubReactorThread负责监听一个selector， 从mainReactor那边accept过来的channel，
 * 会注册到其中的一个selector上，只需要负责其中一个就。
 */
@Slf4j
public class SubReactor implements Runnable {

    Selector selector;
    /**
     * 存储Acctor发送给本reactor上的等待注册的channel task任务
     */
    private LinkedBlockingQueue<Runnable> requestRegisterQueue = new LinkedBlockingQueue<>();


    public SubReactor(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        log.info("==== 子reactor开始运行 ===");
        try {
            while (!Thread.interrupted()) {
                int select = selector.select();
                if (!requestRegisterQueue.isEmpty()) {
                    Runnable registerRunnable = requestRegisterQueue.poll();
                    if (registerRunnable != null) {
                        registerRunnable.run();
                        log.info("注册客户端channelTask 运行成功");
                    }
                } else {
                    if (select == 0) {
                        log.info("发现了select返回，获取到可用key为0");
                        continue;
                    }
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    dispatch(key);
                }
                keys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dispatch(SelectionKey key) {
        Runnable runnable = (Runnable) key.attachment();
        runnable.run();
    }

    /**
     * 向这个subreactor注册客户端的channel。
     *
     * @param socket
     * @param ops
     */
    public Future<SelectionKey> register(SocketChannel socket, int ops) {

        //异步注册， 异步内容为调用selector注册，返回给客户端future。
        RunnableFuture<SelectionKey> registerFuture = new FutureTask<>(() -> {
            SelectionKey selectionKey = socket.register(selector, ops);
            log.info("异步注册任务成功,将客户【{}】注册", socket);
            return selectionKey;
        });

        //添加到注册队列中
        requestRegisterQueue.add(registerFuture);

        //唤醒当前阻塞的selector, 让run方法循环去注册
        selector.wakeup();

        return registerFuture;
    }
}
