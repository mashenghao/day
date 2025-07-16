package iNioCopy;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * 测试 传统文件拷贝到远程网络socket中，这个是服务端
 *
 * @author mahao
 * @date 2022/11/03
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("新socket连接");
            long l = System.currentTimeMillis();
            InputStream in = socket.getInputStream();
            byte[] buffer = new byte[81960];
            long size = 0;
            int readLen = 0;
            while ((readLen = in.read(buffer)) != -1) {
                size += readLen;
            }
            System.out.println("读取数据完成： " + (System.currentTimeMillis() - l) + "  大小:" + size);
            socket.shutdownInput();
            socket.shutdownOutput();
        }
    }
}
