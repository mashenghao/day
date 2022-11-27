package gNio;

import java.nio.ByteBuffer;

/**
 * 1. slice使用：
 * slice是截取了 当前buffer的可读的部分数据，作为一个新的buffer。 与原buffer共享内存。
 *
 * 2.duplicate 完全的复制位置。
 *
 * @author mahao
 * @date 2022/11/24
 */
public class ByteBufferSlice {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < 8; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();

        buffer.get();
        buffer.get();

//        slice是截取了 当前buffer的可读的部分数据，作为一个新的buffer。 与原buffer共享内存。
        ByteBuffer slice = buffer.slice();
        System.out.println(slice);//(position=0,limit-6;cacatiy=6)


        //完全的复制位置。
        ByteBuffer duplicate = buffer.duplicate();
        System.out.println(duplicate);
    }
}
