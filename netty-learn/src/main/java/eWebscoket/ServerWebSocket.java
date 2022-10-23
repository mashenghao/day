package eWebscoket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket08FrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * netty对websocket协议实现测试:
 * 1.  netty 提供了{@link WebSocketServerProtocolHandler} handler用于处理websocket协议。
 * handler中有ws的握手支持,ping、pong响应，close请求这些支持， 同时将Text  与 binary 数据传递
 * 给之后的handler进行业务处理。WebSocketServerProtocolHandler 通过WebSocketServerProtocolHandshakeHandler 首次read请求
 * 确定websocket协议版本(一般是13)， 给channel绑定了不容版本协议下的WebSocket13FrameDecoder。
 *
 * <p>
 * 2.WebSocketServerProtocolHandler在有新的channel连接注册回调方法中{@link WebSocketServerProtocolHandler#handlerAdded(ChannelHandlerContext)},
 * 会在当前handler中添加一个新的handler {@link WebSocketServerProtocolHandshakeHandler}专门用于处理首次客户端请求的握手操作， 查看
 * {@link io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandshakeHandler#channelRead(ChannelHandlerContext, Object)}的接收客户端数据的处理，会为客户返回
 * 一个FullHttpResponse，响应头中，含有确认更新为websocket协议的响应。 当这个channel写会给客户端，此时这个channel的通信协议就有http转为websocket，这个握手用的handler也会
 * 自己移除了。
 * <p>
 * 3. 握手处理过程：
 * 就会将pipLine中用于握手的http的解析handler给移除掉了，
 * p.remove(HttpContentCompressor.class);
 * p.remove(HttpObjectAggregator.class);
 * 然后在 HttpServerCodec 的 handler 之前添加 websocket协议的handler：
 * p.addBefore(ctx.name(), "wsdecoder", newWebsocketDecoder()); {@link WebSocket08FrameDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)}
 * p.addBefore(ctx.name(), "wsencoder", newWebSocketEncoder()); {@link WebSocket08FrameEncoder#encode(io.netty.channel.ChannelHandlerContext, io.netty.handler.codec.http.websocketx.WebSocketFrame, java.util.List)}
 * 之后将response写会客户端成功后升级，将HttpServerCodec移除掉，删除代码在handshake()方法的写会回调中。
 * 在握手升级之后WebSocketServerProtocolHandshakeHandler 没有用了，就会将这个handler，替换成WebSocketServerProtocolHandler.forbiddenHttpRequestResponder()
 * 对非ws的请求拒绝处理。
 *
 * @author mahao
 * @date 2022/10/18
 */
public class ServerWebSocket {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //添加http消息的转换，将socket数据流 转换为 HttpRequest， websocket协议添加这个是为了
                        //握手时候使用。 HttpServerCodec 与 HttpObjectAggregator会在第一次http请求后，被移除掉，握手结束了
                        //协议升级就会只用websocket协议了。
                        pipeline.addLast(new HttpServerCodec());
                        //未知
                        pipeline.addLast(new ChunkedWriteHandler());
                        //将拆分的http消息聚合成一个消息。
                        pipeline.addLast(new HttpObjectAggregator(8096));

                        //用户websocket协议的 握手，ping pang处理， close处理，对于二进制或者文件数据，直接交付给下层
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

                        pipeline.addLast("myHandler", new WebSocketHandler());

                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
        channelFuture.channel().closeFuture().sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }
}
