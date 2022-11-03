package iNioCopy;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author mahao
 * @date 2022/11/03
 */
public class SendFileCopy {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("192.168.31.200", 9999));
        FileChannel channel = new FileInputStream("D:\\视频\\CI&CD\\Jenkins资料.zip").getChannel();
        long l = System.currentTimeMillis();
        long size = 0;
        long limit = channel.size();
        while (size < limit) {
            size += channel.transferTo(size, limit, socketChannel);
        }


        System.out.println("使用sendFile完成数据写入:" + (System.currentTimeMillis() - l) + " 大小:" + size);
        socketChannel.close();
    }
}
