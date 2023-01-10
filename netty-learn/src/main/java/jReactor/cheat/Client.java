package jReactor.cheat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 *
 * @author mahao
 * @date 2022/11/21
 */
public class Client implements Runnable {


    private String ip;

    private int port;

    private SocketChannel socket;

    private ByteBuffer buffer = ByteBuffer.allocate(128);

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            throw new RuntimeException("连接到服务端失败");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                socket.read(buffer);
                if (buffer.position() > 2) {
                    buffer.getShort();










                }
            }
        } catch (IOException e) {

        }

    }


}
