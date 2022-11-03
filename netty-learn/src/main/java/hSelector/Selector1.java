package hSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用selector 与 NIO  只去处理发生事件的channel。
 *
 * @author mahao
 * @date 2022/10/28
 */
public class Selector1 {

    static class Server {
        public static void main(String[] args) throws IOException {

            ByteBuffer buffer = ByteBuffer.allocate(32);

            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(9999);
            serverSocketChannel.bind(socketAddress);
            serverSocketChannel.configureBlocking(false);
            //将serversocketChannel 也注册到选择器上，所以selector的上accepted事件都是来自serversocket，
            //读写事件 是来自serverSocket已经accepted后的socket发生的。
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (selector.select() <= 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    //必须从set中移除，内部set实现对移除应该做了处理，让这个channel的这次事件失效，等待下次事件在此触发。
                    it.remove();

                    if (selectionKey.isAcceptable()) {
                        //socket 连接进来了，将这个连接在注册到selector中。
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                        //此时 accepte不是阻塞的，因为已经有accept事件发生了。
                        SocketChannel socketChannel = channel.accept();
                        //将socketchannel注册到 selector中, 因为是客户端socket 只关注业务读写。
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("客户端加入: " + socketChannel.getRemoteAddress().toString());
                    } else if (selectionKey.isReadable()) {

                        // 读取客户端输入 ，并写入个时间戳
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        buffer.clear();
                        socketChannel.read(buffer);
                        buffer.flip();
                        System.out.println("服务收到消息:" + new String(buffer.array()));

                        buffer.clear();
                        buffer.put((System.currentTimeMillis() + "").getBytes(StandardCharsets.UTF_8));
                        buffer.flip();
                        socketChannel.write(buffer);
                    }
                }

            }
        }
    }

    static class Client {
        public static void main(String[] args) throws Exception {
            for (int i = 0; i < 1; i++) {
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


            Thread.sleep(50000);
        }
    }
}
