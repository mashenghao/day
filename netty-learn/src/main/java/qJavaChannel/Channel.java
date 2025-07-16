package qJavaChannel;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author mash
 * @date 2025/7/15 17:45
 */
public class Channel {

    public static void main(String[] args) throws Exception {
        FileChannel fileChannel = FileChannel.open(Paths.get("source.txt"));
        SocketChannel socketChannel = SocketChannel.open();

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);

    }
}
