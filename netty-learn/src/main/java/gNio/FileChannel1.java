package gNio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author mahao
 * @date 2022/10/25
 */
public class FileChannel1 {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("src/main/resources/file1.txt");
        FileOutputStream fos = new FileOutputStream("src/main/resources/file2.txt");

        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        //put 了三个数据
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 1);
        //此时limit 为3  也就是只能放（0 1 2 ）个数据
        byteBuffer.flip();

        int i = 0;
        while ((i = fisChannel.read(byteBuffer)) > 0) {
            System.out.println(i);
            byteBuffer.flip();

            //这里的写入 对于本地内存 直接写入文件中， 如果是堆内存，先拷贝到本地内存中，之后再去写入。
            fosChannel.write(byteBuffer);
            byteBuffer.flip();
        }

    }
}
