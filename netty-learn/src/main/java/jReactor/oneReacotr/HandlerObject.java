package jReactor.oneReacotr;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * {@link Handler} 替换掉{@link Handler#state} 替换掉用状态标识channel的操作
 * 状态。 通过使用为SelectionKey 重新attach一个新的处理器。
 *
 * @author mahao
 * @date 2022/11/20
 */
public class HandlerObject implements Runnable {

    Selector selector;
    SocketChannel socket;
    SelectionKey selectionKey;
    ByteBuffer buffer = ByteBuffer.allocateDirect(8);

    public HandlerObject(Selector selector, SocketChannel socket) throws IOException {
        this.selector = selector;
        this.socket = socket;
        socket.configureBlocking(false);
        //先设置为0， 标识不对任何事件感兴趣，防止还没注册完成，事件就已经发生了。
        selectionKey = socket.register(selector, 0);
        selectionKey.attach(this);

        //绑定好handler后，重新对读事件感兴趣
        selectionKey.interestOps(SelectionKey.OP_READ);

        //TODO: 未知用处 ？？
        selector.wakeup();

    }

    /**
     * 负责读取操作，构造函数中是对读事件
     */
    @Override
    public void run() {
        try {
            socket.read(buffer);

            process();

            //处理完成后，需要写出数据， 为这个channel改变为写监听
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selectionKey.attach(new Sender());
            selector.wakeup();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void process() {
//        业务处理
    }

    //内部类
    // =======与 handler 的state标志位一样，用来改变对一个channel的不同状态处理  ===
    class Sender implements Runnable {

        @Override
        public void run() {
            buffer.flip();
            try {
                socket.write(buffer);

                //写完后，就可以关闭了，或者重新对读感兴趣
                selectionKey.cancel();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
