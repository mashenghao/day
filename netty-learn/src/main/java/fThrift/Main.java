package fThrift;

import org.apache.thrift.server.*;

/**
 * Thrift 服务端支持的几种网络IO模型：
 *
 * @author mahao
 * @date 2022/10/23
 */
public class Main {

    public static void main(String[] args) {

        /**
         * 1. 最简单的单线程,只能同时处理一个客户端的socket。
         *          服务端ServerSocket 进行 accept，
         *          也在该线程中进行IO流的读写 ，
         *          也在该线程中进行业务processor的处理。
         *          {@link org.apache.thrift.server.TSimpleServer#serve}
         */
        TServer simpleServer = new TSimpleServer(null);

        /**
         *2. 引入线程池来处理socket，可以支持多个客户端同时处理。
         *    线程池使用SynchronousQueue 队列， 主线程一直循环接收请求，
         *    收到的socket连接交由线程池进行读写IO与业务处理。
         *    这个socket连接将一直占用这这个线程池的线程，直到连接退出。
         *    {@link TThreadPoolServer#execute()}
         */
        TServer threadPoolServer = new TThreadPoolServer(null);


        /**
         * 3. 引入NIO网络模型，只选择可读的socket进行读写操作。
         *   主线程select 可用的socket， 然后还是在主线程中
         *   进行读取IO流  与 业务代码执行
         *   {@link org.apache.thrift.server.TNonblockingServer.SelectAcceptThread#select()}
         */
        TServer nonblockingServer = new TNonblockingServer(null);


        /**
         * 4. 优化了下TNonblockingServer 模型，将业务代码的执行放在了单独的线程中进行处理。
         * 是TNonblockingServer的子类，重写了执行业务逻辑的方法实现，改为在线程池中实现。
         *
         * 3和4 两种的模型，读写数据都是在accepted(主线程)中进行读取的，当数据量读写大的时候，大部分时间都
         * 在网络读写(虽然cpu读网卡很块)，也不利于大流量数据处理。
         *
         * {@link THsHaServer#requestInvoke(org.apache.thrift.server.AbstractNonblockingServer.FrameBuffer)}
         */
        THsHaServer hsHaServer = new THsHaServer(null);


        /**
         *5.
         * 定义了三组线程池， 通过将IO的读写放到一个专门的线程池selectorThreads中去处理，
         * 读写完毕后交由invoker再去执行。 acceptThread 只负责接收请求。
         *
         *     private TThreadedSelectorServer.AcceptThread acceptThread;  专门负责处理监听socket上的新连接
         *     private final Set<TThreadedSelectorServer.SelectorThread> selectorThreads = new HashSet(); 负责处理所有的网络I/O请求，只处理IO读写事件。
         *     private final ExecutorService invoker; 负责处理SelectorThread接收到的请求
         *
         * 1. {@link TThreadedSelectorServer.SelectorThread#run()}的this.select()只去寻找 可读 可写的线程再去调用业务线程池进行处理。
         * 2. {@link TThreadedSelectorServer.AcceptThread#select()} 的this.select()只去处理 accepte的socket。
         *
         */
        TThreadedSelectorServer threadedSelectorServer = new TThreadedSelectorServer(null);
    }
}
