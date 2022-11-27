package jReactor.multiReactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author mahao
 * @date 2022/11/21
 */
public class Client implements Runnable {

    static int READ = 0;
    static int WRITE = 1;

    private SocketChannel socket;
    int state = READ;

    //4字节数据长度
    ByteBuffer readBuffer = ByteBuffer.allocate(36);
    ByteBuffer writeBuffer = ByteBuffer.allocate(36);

    public Client(String host, int port) throws IOException {
        socket = SocketChannel.open();
        socket.connect(new InetSocketAddress(host, port));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (state == READ) {
                    read();
                    state = WRITE;
                } else if (state == WRITE) {
                    write();
                    state = READ;
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void write() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入写出数据: ");
        String line = scanner.next();
        byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
        BufferUtil.write(socket, writeBuffer, bytes);
    }

    private void read() throws IOException {

//        // readBuffer 一开始就是写模式
//        while (readBuffer.position() < 4) {
//            socket.read(readBuffer);
//        }
//
//        //切换到读
//        readBuffer.flip();
//        int dataSize = readBuffer.getInt();
//
//        byte[] data = new byte[dataSize];
//
//        //半包处理，数据不够，接着读。
//        int readSize = 0;
//        while (true) {
//            int size = Math.min(readBuffer.remaining(), (dataSize - readSize));
//            readBuffer.get(data, readSize, size);
//            readSize += size;
//            if (readSize >= dataSize) {
//                //可能readBuffer还有数据，整理下数据， 粘包
//                readBuffer.compact();
//                break;
//            } else {
//                //这是半包
//                //切换写
//                readBuffer.clear();
//                socket.read(readBuffer);
//                //切换读
//                readBuffer.flip();
//            }
//        }
//        System.out.println("接收到服务端信息 -> " + new String(data));
        byte[] bytes = BufferUtil.read(socket, readBuffer);
        System.out.println("接收到服务端信息 -> " + new String(bytes));

    }

    /**
     * byteBuf可用大小
     *
     * @param buffer
     * @return
     */
    public static int avaiSize(ByteBuffer buffer) {
        return buffer.limit() - buffer.position();
    }


    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 9999);
        client.run();
    }

}
