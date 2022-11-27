package jReactor;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 经典客户端服务端模式:
 * <p>
 * 1. 服务端每次接收到客户端连接，新开个独立线程去处理任务。 新线程中负责
 * 这个连接的read->decode->compute->encode->send.
 * <p>
 * 2.编程简单，一个线程只负责一个客户端的处理。
 * <p>
 * 3.线程消耗大，每个线程需要占用内存资源，每个线程都要有独立的虚拟机栈，操作数栈，这些栈都是需要jvm创建线程
 * 时，预先分配内存(-Xss=512k)
 *
 * @author mahao
 * @date 2022/11/20
 */
public class ClassicDesign {

    class Server implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(9999);
                while (!Thread.interrupted()){
                    Socket socket = serverSocket.accept();
                    //这里新开个线程去独立处理每个客户端socket。
                    new Thread(new Handler(socket)).start();
                    // 可以使用单线程 或者线程池去处理handler

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 负责对每个socket 进行处理的线程中的方法。
     */
    class Handler implements Runnable {

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] buf = new byte[inputStream.available()];
                int read = inputStream.read(buf);
                //  。。。。
                byte[] process = process(buf);
                //
                socket.getOutputStream().write(process);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /**
         * 这里进行业务计算，计算完成后，写出客户端。
         *
         * @param cmd
         * @return
         */
        private byte[] process(byte[] cmd) {

            // 业务处理

            return new byte[0];
        }
    }

}
