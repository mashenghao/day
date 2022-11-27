package jReactor.oneReacotr;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 每个客户端的socket 添加的处理器
 *
 * @author mahao
 * @date 2022/11/20
 */
public class Handler implements Runnable {

    static int READING = 0;
    static int SENDING = 1;

    private SocketChannel socket;
    private Selector selector;
    SelectionKey selectionKey;
    int state = READING;
    ByteBuffer buffer = ByteBuffer.allocateDirect(8);

    public Handler(SocketChannel socket, Selector selector) throws IOException {
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

    private void read() throws IOException {
        buffer.clear();
        socket.read(buffer);
        //
        //读完后，经过逻辑处理
        process();

        //读取完成后，要变成写事件感兴趣。
        state = SENDING;
        /*
        注意此处 更改了这个channel关注的事件，由之前的读改为写。 之所以调用wakeup，是因为reactor阻塞在
        select上，等待的是这个channel的read事件，如果一直没有读事件，则select会一直阻塞。 因此这里强制
        的调用下wakeup，唤醒select。 好让他重新改变这个channel的关注的事件类型。
         */
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        selector.wakeup();


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


