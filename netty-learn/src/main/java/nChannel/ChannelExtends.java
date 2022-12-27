package nChannel;

import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Channel的继承关系：
 *
 * //1. 在EventLoop的循环方法中，如果对应的事件来了，EventLoop会调用pipeline的fireChannelRegistered 或者 fireChannelRead方法， Pipline的这些方法继承自ChannelInboundInvoker， 如果在pipline的调用，不是在AbstractChannelHandlerContext 意思则是从头开始调用。
 *
 * pipeline.fireChannelRegistered();
 *
 * pipeline.fireChannelRead(readBuf.get(i));
 *
 * //DefaultChannelPipeline的对ChannelInboundInvoker的实现，获取到头，然后执行。
 *     @Override
 *     public final ChannelPipeline fireChannelRead(Object msg) {
 *         //从head。
 *         AbstractChannelHandlerContext.invokeChannelRead(head, msg);
 *         return this;
 *     }
 *
 * //2. 对于Channel中的write这些方法也是继承自ChannelOutboundInvoker，  同时AbstrachChannelContext也是继承自ChannelOutBoundHandler。 触发方法调用也是调用的ChannelOutBoundHandler里面的方法， write 或者 flush 这些调用链的触发的时机只有用户代码在handler里面手动触发才可以。   handler里面手动调用channel.write() 或者ctx.write（），但是不同的是一个是从tail开始调用，一个是从当前handler开始调用。
 *     //channel的write方法实现。
 *     @Override
 *     public ChannelFuture write(Object msg, ChannelPromise promise) {
 *         return pipeline.write(msg, promise);
 *     }
 *     @Override
 *     public final ChannelFuture write(Object msg, ChannelPromise promise) {
 *         return tail.write(msg, promise); //从tail开始掉。
 *     }
 * 	//ctx方法实现。 从当前ctx对应的handler开始调用。
 *     private void write(Object msg, boolean flush, ChannelPromise promise) {
 *         AbstractChannelHandlerContext next = findContextOutbound();
 *         final Object m = pipeline.touch(msg, next);
 *         EventExecutor executor = next.executor();
 *         if (executor.inEventLoop()) {
 *             if (flush) {
 *                 next.invokeWriteAndFlush(m, promise);
 *             } else {
 *                 next.invokeWrite(m, promise);
 *             }
 *         } else {
 *             AbstractWriteTask task;
 *             if (flush) {
 *                 task = WriteAndFlushTask.newInstance(next, m, promise);
 *             }  else {
 *                 task = WriteTask.newInstance(next, m, promise);
 *             }
 *             safeExecute(executor, task, promise, m);
 *         }
 *     }
 *
 * //ChannelOutBoundInvoker中有些方法，系统也会默认调用的，比如bind()方法，当ServerBootStrap将ServerSocketChannel注册到EventLoop上后，就会 主动触发channel.bind()方法， 整个bind链路调用，head.bind()真正的工作javaChannel.bind()；
 * //当BootStrap将客户端channel注册到事件循环上面后，就会调用channel.connect(),head的connect真正实现了到服务的连接命令。
 *
 * 、、、、、、、、、、、、、、、、、、、、、NioSocketChannel继承链路分析与说明：、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
 * NioSocketChannel 继承链路分析与说明：
 * 1. Channel: 声明一个Channel应该有的能力，包括获取Unsafe unsafe()的能力。 channel中拥有对连接的状态获取，地址获取，	pipline处理链路获取。 以及Unsafe的能力。
 * Unsfae：最上层的声明，声明了channel中的unsafe操作， 注册，连接，写数据，flush出，关闭，获取读缓冲，获取写缓冲，
 *        void beginRead(); 不知道是干啥的。
 *
 * 2. AbstractChannel：定义了一些基础骨架， 对ChannelOutboundInvoker的方法进行了实现能力，都改成了调用pipline进行实现，从链路的尾向head调用链。 除此之外就是对一些channel状态的实现。 内部声明了抽象模版方法doRegister(),doBind(),doBeginRead(),doWrite()，针对具体的channel做一些具体的操作。
 * AbstractUnsafe: Unsafe中的方法，在调用完如register应该触发ChannelHandler的回调链路，这些实际的注册操作，是调用的AbstractChannel的模版方法，而AbstractUnsfae调用的是这些模版方法，加上回调链路的实现。
 *     对于ChannelInbountHandler的回调起始调用都是AbstractUnsafe中实现的，比如调用channel的unsafe的register，AbstractUnsafe就触发pipline的调用链路了。一般对于inbound的方法，触发开始都在AbstractUnsafe中，因为unsfae是真正的操作，只有真正操作完成后，才能触发回调。
 *    对于ChannelOutbound的相关的操作，如close，write、这些真正的写出是在AbstractUnsafe中，abstractunsafe就只管写出数据了，没有再去触发回调的工作了。 这些真正写出操作，是在整个输出链路的最后，因此调用写出这些的方法则是在head进行调用了。
 *         //head操作。
 *         @Override
 *         public void read(ChannelHandlerContext ctx) {
 *             unsafe.beginRead();
 *         }
 *
 *         @Override
 *         public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
 *             unsafe.write(msg, promise);
 *         }
 *
 *         @Override
 *         public void flush(ChannelHandlerContext ctx) throws Exception {
 *             unsafe.flush();
 *         }
 *
 * 3. AbstractNioChannel: 基于Selector对一些模版进行实现，将其注册到selector上，但是没有对读方法进行实现。
 *    NioUnsafe: 新增了一个read()操作，读channel进行读取，结果存到receBuffer中。
 *    AbstractNioUnsafe: 没有做啥特殊操作，也没有对read进行实现。
 *
 * 4. AbstractNioByteChannel： 对AbstractNioChannel的read()方法进行实现，表名每次read的数据是字节数组，这里读写出的数据的模版方法进行了实现，写出的数据类型已经确认了，是字节数组。
 *    NioByteUnsafe： 对read方法进行了模版实现，先分配内存，调用模版方法读取到字节后，调用channelRead链路。
 *
 * 5. NioSocketChannel： 实现具体如何读取字节的，写出字节的操作。
 *
 *
 * ////////////////////////NioServerSocketChannel 继承分析说明////////////////////////////////////////////
 *
AbstractNioMessageChannel： 对read中方法进行了实现，已经实现了将读取的对象值存到list中，触发channelRead等，
但是真正的读取结果方法还是抽象的，将有ServerSocketChannel去实现。

ServerSocketChannel实现了读取消息，也就是accept，消息类型是SocketChanenl。
 *
 *
 *
 * @author mahao
 * @date 2022/12/07
 */
public class ChannelExtends {

    public static void main(String[] args) {
        NioSocketChannel nioSocketChannel = new NioSocketChannel();
        NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();




    }
}
