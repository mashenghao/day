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
 * 再次测试 nio  select的使用
 *
 * @author mash
 * @date 2025/7/15 15:50
 */
public class SelectTest {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int select = selector.select();
            if (select <= 0) {
                System.out.println("唤醒了，数据为空");
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    System.out.println(socketChannel + "  获取到新的channel ac进来");
                    new Thread(() -> {
                        try {
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ); // register 会被阻塞主。  如果register的时候，阻塞了，但是客户段发消息了，channel只要有数据，select下次还是会被唤醒的。
                            selectionKey.interestOps(selectionKey.interestOps() & SelectionKey.OP_ACCEPT);

                            System.out.println(socketChannel + "   获取到新的channel 注册完成");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }).start();
                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    System.out.println(channel + "   channel 可以读取了");

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer);
                    System.out.println(channel + "   channel 收到消息了" + new String(byteBuffer.array()));
                    byteBuffer.clear();
                }
            }
        }
    }

    static class Client {
        public static void main(String[] args) throws Exception {
            for (int i = 0; i < 2; i++) {
                new Thread(() -> {
                    try {
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

                        System.out.println("134");
                        socketChannel.close();
                        Thread.sleep(20000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }).start();
            }


            Thread.sleep(5000000);
        }
    }
}
