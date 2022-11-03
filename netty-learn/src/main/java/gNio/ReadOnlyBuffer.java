package gNio;

import java.nio.ByteBuffer;

/**
 * 1.  测试将一个buffer转为只读的buffer。 只读buffer不允许修改。
 * <p>
 * put方法会抛出异常。
 * <p>
 * 2. 只读的buffer的数据会跟随老的变动，他们共享使用同一个数据数组。
 *
 * @author mahao
 * @date 2022/10/27
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(10);
        for (int i = 0; i < 10; i++) {
            allocate.put((byte) i);
        }

        ByteBuffer buffer = allocate.asReadOnlyBuffer();

        //1. 修改时，会报错ReadOnlyBufferException
//        buffer.put((byte) 1);

        //2. 当老的buffer变动时，新的只读buffer也会变动。
        allocate.put(1, (byte) 100);
        buffer.flip();
        while (buffer.hasRemaining()) {
            //0 100 2 3 4 5 6 7 8 9
            System.out.print(buffer.get() + " ");
        }


    }
}
