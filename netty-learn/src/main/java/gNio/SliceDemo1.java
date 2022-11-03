package gNio;

import java.nio.ByteBuffer;

/**
 * @author mahao
 * @date 2022/10/27
 */
public class SliceDemo1 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < 10; i++) {
            buffer.put((byte) i);
        }

        buffer.position(1).limit(5);

        //        return new HeapByteBuffer(hb,
        //                                        -1,
        //                                        0,
        //                                        this.remaining(),
        //                                        this.remaining(),
        //                                        this.position() + offset);

        //就是 共享同一个数组，position为0，limit为还剩多少数据， 设置了偏移量不为0，是position的值。
        ByteBuffer sliceBuffer = buffer.slice();
        while (sliceBuffer.hasRemaining()) {
            System.out.println(sliceBuffer.get() + " ");
        }

        int capacity = sliceBuffer.capacity();
        System.out.println(capacity);

    }
}
