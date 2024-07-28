package jReactor.multiReactor;

import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * @author mahao
 * @date 2022/11/20
 */
@Slf4j
public class HandlerMultiThread implements Runnable {

    ExecutorService businessExecutors;

    static int READING = 0;
    static int SENDING = 1;
    static int PROCESSING = 3;

    private SocketChannel socket;
    SelectionKey selectionKey;
    int state = SENDING;
    ByteBuffer input = ByteBuffer.allocateDirect(32);
    ByteBuffer output = ByteBuffer.allocateDirect(32);

    public HandlerMultiThread(SubReactor subReactor, SocketChannel socket, ExecutorService businessExecutors) throws IOException {
        this.socket = socket;
        this.businessExecutors = businessExecutors;
        socket.configureBlocking(false);
        //先设置为0， 标识不对任何事件感兴趣，防止还没注册完成，事件就已经发生了。
//        selectionKey = socket.register(subReactor.selector, 0);

        //优化为使用SubReactor 封装方法注册， 如果直接在当前线程中注册，selector阻塞在select中，注册方法也会一直阻塞。
        //所以交由 subReactor.register(socket,0)方法注册，该方法是异步的，将待注册的客户端写到队列中，然后唤醒sunReactor对应的
        //线程去注册，注册成功后，则需要SelectKey返回过来。
        try {
            selectionKey = subReactor.register(socket, 0).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException("注册失败", e);
        }

        //添加事件处理器，为了reactor可以获取到每个channel的事件处理器。 添加成功后，才对写感兴趣
        selectionKey.attach(this);

        //服务端接收到连接后，先将接入提示信息写入缓存，等到轮询可写的时候，再去写。
        this.write("欢迎接入欢迎接入".getBytes(StandardCharsets.UTF_8));
        //对写感兴趣。
        selectionKey.interestOps(SelectionKey.OP_WRITE);

        //TODO: 用来唤醒Selector调用select一直处于阻塞状态。
        subReactor.selector.wakeup();
    }

    /**
     * 针对读完后，要写的操作，可以优化为，将状态标识去掉， 用对象代替。 替换掉selectKey中的处理器。
     */
    @Override
    public void run() {
        try {
            if (state == READING)
                read();
            if (state == SENDING)
                send();
        } catch (Exception e) {
            try {
                log.error("关闭连接:::" + e.getMessage());
                socket.close();
                selectionKey.cancel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * 往channel里面写。
     */
    private void send() throws IOException {
        int writeSize = socket.write(output);
        if (outputIsComplete(writeSize)) {
            //写完了，重新对读兴趣]

            //写完对读感兴趣
            state = READING;
            selectionKey.interestOps(SelectionKey.OP_READ);
            log.info("wakeup  ------ 写完成改为读兴趣，");
            selectionKey.selector().wakeup();//立即唤醒，新状态变动
        }
    }

    /**
     * 写完成了
     *
     * @param writeSize
     * @return
     */
    private boolean outputIsComplete(int writeSize) throws EOFException {
        if (writeSize == -1) {
            throw new EOFException("写出的返回值 -1 ");
        }
        //为空表示写完了。
        return !output.hasRemaining();
    }

    /**
     * sockerChannel是非阻塞的，调用read读取数据时，如果没有数据了，不会等待，而是立刻返回
     * 读取到的数据为0. 此时的read方法，不能循环请求数据，而是结束这次读取, 保存下来已经读到的数据，
     * 等待下次读事件来到，接着上次的读。
     *
     * @throws IOException
     */
    //TODO: read 也加同步锁原因
    private synchronized void read() throws IOException {
        //每次进入到读取的方法，必须保证input中的数据都是当前读的。
        int readSize = socket.read(input);
        if (inputIsComplete(readSize)) {
            //读完后，业务处理，是在线程池中进行异步处理。
            //input转为对象数据
            state = PROCESSING;
            businessExecutors.execute(() -> {
                //TODO: 必须加同步锁的原因  为了和外层的read方法互斥，两个方法只能执行一个。 不让业务读的同时，再去进行read下一次channel read的操作，
                //他们可能共享成员变量，导致并发问题。
                synchronized (HandlerMultiThread.this) {

                    //为了让sliceBuffer中数据都是本次自己读的数据，如果有粘包的话，粘包数据不应该包括
                    int dataSize = input.getInt();
                    int writeIdx = input.limit(); //记录已经写的索引位置
                    input.limit(dataSize + 4);
                    ByteBuffer sliceBuffer = input.slice();

                    //业务逻辑
                    process(sliceBuffer);

                    //这里整理下，input数据，如果有数据粘包，将数据
                    //当前这次读取数据已经结束了，粘包的数据compact 整理到最前面
                    input.position(dataSize + 4);
                    input.limit(writeIdx);
                    input.compact();

                    //读取完成后，要变成写事件感兴趣。
                    state = SENDING;
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                    log.info("wakeup  ------ 读完成改为写兴趣，");
                    selectionKey.selector().wakeup();//立即唤醒，新状态变动
                }
            });
        } else {
            //没有读取完成，不做任何操作，结束这次读，等待下次读事件发生。
        }
    }

    private boolean inputIsComplete(int readSize) throws EOFException {
        if (readSize == -1) { //当client关闭连接时，这里会被唤醒，并且读取到-1
            throw new EOFException("数据读到的是-1");
        }
        if (readSize == 0) {
            log.info("读事件被其他任务唤醒,读取数据为0");
            return false;
        }

        if (input.position() < 4) {
            //buffer 中数据小于4字节， 没读到数据大小，继续读
            return false;
        }

        //记录当前写的位置，如果数据没读完，在此进行读取。
        int writeIdx = input.position();

        //切换到读模式,开始读取数据
        input.flip();
        int dataSize = input.getInt();

        //判断下是否需要扩容
        if (input.capacity() < dataSize + 4) {
            ByteBuffer newInput = ByteBuffer.allocate(dataSize + 4);
            newInput.putInt(dataSize);
            newInput.put(input);

            input = newInput;
            //重新切换为读取的模式
            input.flip();
            //重新定位到数据位置。
            input.position(4);

            log.info("input大小不够，扩容到[{}]", dataSize + 4);
        }
        if (input.remaining() >= dataSize) {
            //可读的数据量大于 要求的数据数
            input.position(0);
            return true;
        } else {
            //半包了，input切换为写模式， 接着从socket中读
            input.position(writeIdx);
            input.limit(input.capacity());
            return false;
        }
    }

    /**
     * 将数据写入buffer中，等待channel可写时，再去写数据
     *
     * @param bytes
     */
    private void write(byte[] bytes) {

        output.clear();

        //扩容
        if (output.capacity() < bytes.length + 4) {
            output = ByteBuffer.allocate(bytes.length + 4);
        }
        output.putInt(bytes.length);
        output.put(bytes);
        //output切换为读模式，等待写。
        output.flip();
    }


    private void process(ByteBuffer buffer) {
        byte[] dates = new byte[buffer.remaining()];
        buffer.get(dates, 0, dates.length);
        System.out.println(Thread.currentThread().getName() + " : 接收到客户端信息 -> " + new String(dates));

        //读取完成后调用提供的write写入数据，其实写的只是buffer中。 等到下一轮select的时候，才会去写。
        String msg = Thread.currentThread().getName() + "服务端:" + getRandomString(new Random().nextInt(100));
        write(msg.getBytes(StandardCharsets.UTF_8));

    }


    //length用户要求产生字符串的长度
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
