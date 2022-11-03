package iNioCopy;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 使用MappedByteBuffer 虚拟内存映射，减少磁盘文件拷贝到用户空间的拷贝。
 * 这种方式是零拷贝的实现方案之一，数据无需拷贝到用户空间，直接在内核空间操作，写入socket的时候，
 * 也不需要经过用户内存中转，直接内核缓冲区拷贝到socket缓冲区。
 * <p>
 * RocketMQ: https://juejin.cn/post/7076819003204698119 使用mmap 将数据写入到磁盘中。
 *
 * @author mahao
 * @date 2022/11/03
 */
public class MappedByteBuffer {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("192.168.31.200", 9999));
        FileChannel readChannel = FileChannel.open(Paths.get("D:\\视频\\CI&CD\\Jenkins资料.zip"), StandardOpenOption.READ);
        java.nio.MappedByteBuffer byteBuffer = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, readChannel.size());
        long l = System.currentTimeMillis();
        long size = 0;
        int write = socketChannel.write(byteBuffer);
        System.out.println(write);


        System.out.println("使用mmap虚拟内存技术IO拷贝完成耗时:" + (System.currentTimeMillis() - l) + " 大小:" + write);
        socketChannel.close();
    }
}
