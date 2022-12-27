package aHello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 定义自己的处理逻辑，ChannelHandlerContext是责任链的实现core。
 * 这个实现类是channel - read 阶段时候被回调的。
 *
 * @author mahao
 * @date 2022/10/12
 */
public class HelloClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    private String str;

    public HelloClientHandler(String str) {
        this.str = str;
    }

    /**
     * @param ctx 用来实现pip line责任链模式的。
     * @param msg 之前的 pip line 的处理结果。
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.err.println(str);
        System.out.println(msg);
        ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
        //构造处 http协议的返回体返回。
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

//        ctx.fireChannelRead(msg);

        //pipline 从当前handler为最后一个OutBoundHandler开始向外写出。
//        ctx.writeAndFlush(response);

        //pipLine 从pipline的tail对应handler为最后一个开始写出。
        ctx.channel().writeAndFlush(response);
    }


}
