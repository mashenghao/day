package gNio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 堆外内存理解:
 * 1. 使用堆外内存的主要原因是，在Channel的读写操作中，如果Buffer类型是DirectBuffer,直接将数据写入到这个buffer中了。
 * 如果是HeapBuffer的话，需要先将数据拷贝到对外内存中，之后在将堆外内存拷贝到堆内的buffer中。
 * 查看{@link java.nio.channels.FileChannel#read(ByteBuffer)}可看到。
 * <p>
 * 2.底层通过write、read、pwrite,pread函数进行系统调用时,需要传入buffer的起始地址和 buffer count。
 * 使用channel 必须要用堆外内存的原因是GC，GC会移动对象位置，传给操作系统的内存地址不再是之前对象的位置 了。
 * C Heap中我们分配的虚拟地址空间是连续的，java heap的buffer可以是不连续的。
 * <p>
 * 3.传统的socket 的write(byte[]) 或者 文件的write(byte[]) 在本地方法实现中，也是需要将java的heap data 做一次拷贝，拷贝到
 * 用c的malloc分配的一块内存中，分配到内存中后，再去调用操作系统的api实现写入操作。 nio中Direc Buffer 将这个操作暴露了出来。
 *
 * @author mahao
 * @date 2022/10/27
 */
public class DirectBuffer {

    public static void main(String[] args) throws IOException {

        //1. 直接使用堆外内存操作，写入文件 或者 操作系统 或者 提供channel进行读写 ，则少了一次从堆内内存 到堆外内存的拷贝操作。
        //在channel的 读写时，会判断bytebuffer是否是堆内的，如果是手动分配临时一部分堆外内存，将数据拷贝到对外内存后，再去调用channel。
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        for (int i = 0; i < 10; i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            System.out.print(byteBuffer.get() + "  ");
        }

        //2. 普通文件 或者socket的写入字节数组的操作，也是需要在c代码中申请内存，将java的数组，拷贝到新申请的
        //内存后，再去调用操作系统api。


        //可以读取到多个ByteBuffer中。
        RandomAccessFile file = new RandomAccessFile("src/main/resources/1.txt","rw");
        file.getChannel().read(new ByteBuffer[0]);

    }
}
