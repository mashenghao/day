package gNio;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * @author mahao
 * @date 2022/11/21
 */
public class ByteBufferTest {

    public static void main(String[] args) {

        //写模式
        ByteBuffer buffer = ByteBuffer.allocate(8);
        for (int i = 0; i < 4; i++) {
            buffer.put((byte) i);
        }
        System.out.println(buffer);

        //切换读模式
        System.out.println("读取模式切换");
        buffer.flip();
        System.out.println(buffer);
        byte[] bytes = new byte[8];
        buffer.get(bytes, 0, 2);
        System.out.println(buffer);

        System.out.println("compact");
        buffer.compact();
        //重新恢复读位置。
        buffer.flip();
        System.out.println(buffer);

        //

    }


    //需要完成用bytebuffer，循环利用。

}
