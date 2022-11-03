package iNioCopy;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 使用堆外内存拷贝，减少一次从堆内内存到堆外内存的拷贝。
 * 使用ByteBuffer 分配堆外内存。
 *
 * @author mahao
 * @date 2022/11/03
 */
public class DirectCopy {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("192.168.31.200", 9999));
        FileInputStream fis = new FileInputStream("D:\\视频\\CI&CD\\Jenkins资料.zip");
//        ByteBuffer buffer = ByteBuffer.allocate(fis.available());
        //减少一个堆内内存到堆外内存的拷贝。
        ByteBuffer buffer = ByteBuffer.allocateDirect(fis.available());
        FileChannel channel = fis.getChannel();
        long l = System.currentTimeMillis();
        long size = 0;
        while (channel.read(buffer) != -1) {
            buffer.flip();
            size += buffer.limit();
            socketChannel.write(buffer);
            buffer.clear();
        }

        System.out.println("使用堆外内存IO拷贝完成耗时:" + (System.currentTimeMillis() - l) + " 大小:" + size);
        socketChannel.close();
    }
}
