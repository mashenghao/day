package iNioCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 传统的使用socket拷贝。
 * 会涉及到5次内存拷贝操作，两次上下文切换。
 * 1.读取: 从本地磁盘文件 -(通过DMA技术拷贝内核缓冲区)->  内核缓冲区  -(cpu拷贝到用户缓冲区，也就是堆外内存)-> 堆外内存 -->拷贝到堆外内存
 * 2.写入: 这时候也需要从堆内内存 -> 堆外内存 -> socket缓冲区 -> DMA拷贝网卡中
 *
 * @author mahao
 * @date 2022/11/03
 */
public class BIOCopy {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.31.200", 9999);
        OutputStream ous = socket.getOutputStream();
        FileInputStream fis = new FileInputStream("D:\\视频\\CI&CD\\Jenkins资料.zip");
        byte[] buffer = new byte[fis.available()];
        long l = System.currentTimeMillis();
        long size = 0;
        while (fis.read(buffer) != -1) {
            size += buffer.length;
            ous.write(buffer);
        }
        ous.flush();
        System.out.println("传统IO拷贝完成耗时:" + (System.currentTimeMillis() - l) + " 大小:" + size);
        socket.close();
    }
}
