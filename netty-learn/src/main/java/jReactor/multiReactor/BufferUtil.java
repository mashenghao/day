package jReactor.multiReactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author mahao
 * @date 2022/11/23
 */
public class BufferUtil {

    public static byte[] read(SocketChannel socket, ByteBuffer readBuffer) throws IOException {

        // readBuffer 一开始就是写模式
        while (readBuffer.position() < 4) {
            socket.read(readBuffer);
        }

        //切换到读
        readBuffer.flip();
        int dataSize = readBuffer.getInt();

        byte[] data = new byte[dataSize];

        //半包处理，数据不够，接着读。
        int readSize = 0;
        while (true) {
            int size = Math.min(readBuffer.remaining(), (dataSize - readSize));
            readBuffer.get(data, readSize, size);
            readSize += size;
            if (readSize >= dataSize) {
                //可能readBuffer还有数据，整理下数据， 粘包
                readBuffer.compact();
                break;
            } else {
                //这是半包
                //切换写
                readBuffer.clear();
                socket.read(readBuffer);
                //切换读
                readBuffer.flip();
            }
        }
        return data;
    }


    public static void write(SocketChannel socket, ByteBuffer writeBuffer, byte[] dates) throws IOException {
        writeBuffer.clear();
        writeBuffer.putInt(dates.length);
        writeBuffer.flip();
        socket.write(writeBuffer);

        int writeSize = 0;
        while (writeSize < dates.length) {
            writeBuffer.clear();
            int size = Math.min(writeBuffer.capacity(), dates.length - writeSize);
            writeBuffer.put(dates, writeSize, size);
            writeSize += size;
            writeBuffer.flip();
            socket.write(writeBuffer);
        }
    }
}
