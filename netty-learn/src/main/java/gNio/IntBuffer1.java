package gNio;

import java.nio.IntBuffer;

/**
 * java 提供的一些nio的操作类：IntBuffer
 * <p>
 * Buffer的三个int变量 position limit capacity
 * 0 <= position <= limit <= capacity
 * 属性：
 * position表示当前读取或者写入的Buffer的索引位置。
 * limit 表示读取或者写入的终止索引位置
 * capacity 表示buffer的大小，该大小不会变动
 * mask 标记当前position的位置，调用reset()会将position回退到mask的值
 * <p>
 * 方法：
 * flip(): 将limit 置为 position位置，position置为0， 意思是为了get() 或者 channel-write用(读取数据写入channel)。
 * clear(): 重置这个buffer
 * get(): 去取buffer里面数据   put(): 写入数据
 *
 * @author mahao
 * @date 2022/10/25
 */
public class IntBuffer1 {

    public static void main(String[] args) {

        //1. 这里创建了IntBuffer，内部创建了堆上的int数组。
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < 10; i++) {
            //往数组放数据
            buffer.put(i);
        }

        //
        buffer.flip();

        while (buffer.hasRemaining()) {
            int i = buffer.get();
            System.out.print(i + " ");
        }

        buffer.flip();

        System.out.println();
        while (buffer.hasRemaining()) {
            int i = buffer.get();
            System.out.print(i + " ");
        }

        //清空buffer
//        buffer.clear();

        //标记开始读的位置，当reset后，在此恢复到这个位置。
        buffer.mark().reset();
    }
}
