package jReactor.oneReacotr;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程版本的handler。
 * 将业务处理独立出来
 *
 * @author mahao
 * @date 2022/11/20
 */
public class HandlerMultithreaded implements Runnable {

    ExecutorService executors = Executors.newCachedThreadPool();

    static int READING = 0;
    static int SENDING = 1;
    static int PROCESSING = 3;

    private SocketChannel socket;
    private Selector selector;
    SelectionKey selectionKey;
    int state = READING;
    ByteBuffer buffer = ByteBuffer.allocateDirect(8);

    public HandlerMultithreaded(SocketChannel socket, Selector selector) throws IOException {
        this.selector = selector;
        this.socket = socket;
        socket.configureBlocking(false);
        //先设置为0， 标识不对任何事件感兴趣，防止还没注册完成，事件就已经发生了。
        selectionKey = socket.register(selector, 0);
        //添加事件处理器，为了reactor可以获取到每个channel的事件处理器
        selectionKey.attach(this);

        //对读 兴趣
        selectionKey.interestOps(SelectionKey.OP_READ);

        //TODO: 未知用处
        selector.wakeup();
    }

    /**
     * 针对读完后，要写的操作，可以优化为，将状态标识去掉， 用对象代替。 替换掉selectKey中的处理器。
     */
    @Override
    public void run() {
        try {
            if (state == READING)
                read();
            if (state == SENDING)
                write();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private synchronized void read() throws IOException {
        //此处的优化是读取还在Reactor的主线程读取
        buffer.clear();
        socket.read(buffer);
        //

        //读完后，业务处理，是在线程池中进行异步处理。
        state = PROCESSING;
        executors.execute(() -> {
            //也要加锁
            synchronized (HandlerMultithreaded.this) {
                process();

                //读取完成后，要变成写事件感兴趣。
                state = SENDING;
                selectionKey.interestOps(SelectionKey.OP_WRITE);

                //这里不用加，但是要加同步锁
//            selector.wakeup();
            }
        });
    }

    private void write() throws IOException {
        buffer.flip();
        socket.write(buffer);

        //写完后，重新对读感兴趣。
//        state = READING;
//        selectionKey.interestOps(SelectionKey.OP_READ);

        //或者关闭
        selectionKey.cancel();
    }


    private void process() {
        //TODO 业务处理
    }
}
