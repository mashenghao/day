package cn.mh.aHello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 3. 设置channel的处理pip line,数据处理线，用了责任链模式。
 * <p>
 * ChannelInitializer 类是当 ServerChannel 接收到客户端的channel的accept后，
 * 进行的一个回调。 回调的意义是为了设置这个客户端channel的处理过程， 直接说就是当channel可以
 * 进行读取数据的时候，对读取到的数据如何进行处理的过程。
 * <p>
 * 当然，也可以定义读取到数据后， 写回给客户端什么信息，都是在这里定义好的。
 *
 * @author mahao
 * @date 2022/10/12
 */
public class HelloInitializer extends ChannelInitializer<SocketChannel> {


    /**
     * 在channel的生命周期中，这个方法是在 channelRegistered() 时，也就是在channel注册的到loop group的时候，
     * 会被调用。ChannelInitializer 也是ChannelInboundHandler的各个回调接口的一个实现类，该实现类关注的channelRegistered这个回调结口。
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        // 获取channel 的处理流水线
        ChannelPipeline pipeline = ch.pipeline();

        //3.1 为流水线添加自己的解析，已有的流水线，应该是处理socket中的流数据，这里添加
        //个http的解析器，将数据解析为http信息。
        pipeline.addLast("httpServerCodec", new HttpServerCodec());

        //3.2 解析成http信息后，定义自己业务处理逻辑，这里定义了一个handler，简单的写会数据。
        pipeline.addLast("helloHander", new HelloClientHandler(" --- helloHander ---"));
        
        //验证helloHander 不调用channelRead0的fireChannelRead，这个hellohandler2 就不会调用，这个责任链就无法向下传递。
        pipeline.addLast("helloHander2", new HelloClientHandler("----  helloHander2  ----"));
    }


}
