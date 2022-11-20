package hSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 测试selector的wakeUp方法。
 *
 * 当selector的select方法在阻塞时，此时改动channel关注的类型， 即使channel变为关注的类型，也无法感知到，需要
 * 手动调用wakeup，唤醒下一波select。
 *
 * @author mahao
 * @date 2022/11/20
 */
public class SelectorWakeup {

    static class Server {
        public static void main(String[] args) throws IOException {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9999));
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
            selectionKey.attach("对读事件感兴趣");
            int i = 0;
            while (true) {
                System.out.println("处理第" + i++);
                int select = selector.select();
                if (select == 0) {
//                    被手动wakeUp唤醒的话，则是0.
                    return;
                }
                System.out.println("获取的数量为：" + select);
                Set<SelectionKey> keys = selector.selectedKeys();
                System.out.println("keys的数量为：" + keys);
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (SelectionKey.OP_READ == key.interestOps()) {
                        System.out.println(key.attachment().toString());
                        ByteBuffer buffer = ByteBuffer.allocate(32);
                        ((SocketChannel) (key.channel())).read(buffer);
                        System.out.println("客户端的信息--->" + new String(buffer.array()));
                        new Thread(() -> {
                            /*
                            异步改变channel关心的事件， 如果当前的selector已经在select阻塞中了。 channel之前是read感兴趣， 此时select监听的channel
                            也一直是read。 异步更改为write 是监听不到的，此时必须调用walkup，强制唤醒
                             */


                            sleep(10);
                            key.attach("对写事件感兴趣");
                            key.interestOps(SelectionKey.OP_WRITE);
                            System.out.println("异步设置对写事件感兴趣");
                            /**
                             * 需要调用
                             */
                            selector.wakeup();

                        }).start();

                    } else if (SelectionKey.OP_WRITE == key.interestOps()) {
                        ByteBuffer buffer = ByteBuffer.allocate(32);
                        buffer.put((System.currentTimeMillis() + "").getBytes(StandardCharsets.UTF_8));
                        ((SocketChannel) (key.channel())).write(buffer);
                        key.cancel();

                    } else {
                        System.out.println("不应该出现的====");
                    }
                }

                System.out.println("结束第" + i);
            }
        }
    }

    static class Client {
        public static void main(String[] args) throws IOException {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 9999));

            ByteBuffer buffer = ByteBuffer.allocate(32);
            buffer.put(("你好,我是客户端" + Thread.currentThread().getName()).getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);

            buffer.clear();
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println("服务端回复：" + new String(buffer.array()));
        }
    }


    public static void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
