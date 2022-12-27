package oChannelHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * channel Handler的生命周期 与 Pipline的调用接口。
 *
 * @author mahao
 * @date 2022/11/07
 */
public class LiftOutServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup)
                .handler(new ChannelDuplexHandler() {
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss--1   --handlerAdded--- handler添加。");
                    }

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-in-1 --- channelRegistered -- 当serverChannel的注册到EventLoop上后，register方法被调用。");
                        ctx.fireChannelRegistered();
                    }

                    @Override
                    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                        System.out.println("ss-out-2 --- bind --当serverChannel的注册成功后，ServerBootStrap添加的回调函数会去做bind操作，bind的head真正实现绑定");
                        ctx.bind(localAddress, promise);
                    }

                    @Override
                    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                        System.out.println("ss-out- N --- connect-- serverChannel 中不会调用的，只有bootStrap才有这个connect操作。");
                    }

                    //io.netty.channel.AbstractChannel.AbstractUnsafe.bind
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-in-3 --- channelActive -- 不确定serverChanenl的这个方法有没有被回调到。应该有回调的。 socketchannel的一定有。" +
                                "  后来确认ss也有调用。 当bind成功后，就会添加一个actice的任务。 当channelActive 被调用后， 在head的active方法中，会去主动" +
                                "触发read()操作， outbound的read方法将被回调。");

                        ctx.fireChannelRegistered();
                    }


                    /**
                     * head中读完了， 就会回调。
                     *         public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                     *             ctx.fireChannelReadComplete();
                     *
                     *             readIfIsAutoRead();
                     *         }
                     *
                     *所以ChannelOutboundHandler上的read方法，如其注释所述，是为了拦截ChannelHandlerContext.read()操作。
                     * 也就是说，ChannelOutboundHandler可以通过read()方法在必要的时候阻止向inbound读取更多数据的操作。
                     * 这个设计在处理协议的握手时非常有用。
                     * @param ctx
                     * @throws Exception
                     */
                    @Override
                    public void read(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-out-4 --- read --  在channelActive这个inbount方法中，被调用。 或者读事件结束后，也被调用了。 意义是通知hean开启新一轮的读。");
                        super.read(ctx);
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println("ss-in-4 --- channelRead -- 当serverchannel有事件到来时，就是read了，读的就是socketchannel。");
                        ctx.fireChannelRead(msg);
                    }

                    @Override
                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-in-5 --- channelReadComplete -- serverChanenl读完了。");
                        ctx.fireChannelReadComplete();
                    }

                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        System.out.println("ss-out-6 --- write -- 要调用serverChannel去写数据，没有要写的操作。");
                        ctx.write(msg, promise);
                    }

                    @Override
                    public void flush(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-out-7 --- flush --");
                        ctx.flush();
                    }

                    @Override
                    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                        System.out.println("ss-out-8 --- disconnect -- 服务端 主动发起断开连接。");
                        ctx.disconnect(promise);
                    }

                    @Override
                    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                        System.out.println("ss-out-8 --- close -- 服务端 主动调用close方法。");
                        ctx.close();
                    }

                    @Override
                    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                        System.out.println("ss-out-9 --- deregister -- ");
                        super.deregister(ctx, promise);
                    }


                    @Override
                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-out-in --- channelUnregistered -- ");
                        super.channelUnregistered(ctx);
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-out-in --- channelInactive -- ");
                        super.channelInactive(ctx);
                    }


                    @Override
                    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss-out-in --- channelWritabilityChanged -- ");
                        super.channelWritabilityChanged(ctx);
                    }

                    @Override
                    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("ss - 1 - handlerRemoved");
                    }
                })
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ch.pipeline().addFirst(new HttpServerCodec());
                        ch.close();
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                            //1. 当channel注册到EventLoop后, 未做bind/connect操作前，就会先去添加handlerAdd。ChannelInit就是发生这里。
                            @Override
                            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("1. in - handlerAdded 调用");
                            }

                            //2. handler注册到eventLoop，执行完handlerAdd方法之后，就会去执行注册方法。
                            @Override
                            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("2. in  - channelRegistered 调用");
                                ctx.fireChannelRegistered();
                            }

                            //3. 当channel被下一次EventLoop循环执行任务，去做完bind操作后，这个active就会被执行。
                            //这个是客户端channel，会被立即调用。
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("3. in - channelActive 调用--- 注册完后，如果是客户端channel 就立即调用，ServerChanenl的是bind之后调用。");
                                ctx.fireChannelActive();
                            }

                            //4. 当channel有数据读取时，就是执行read。
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
                                System.out.println("4. in - channelRead0 调用");
                            }

                            //5. 当本次read操作完成后， 就会触发读取完成。
                            @Override
                            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("5. in - channelReadComplete 调用");
                                ctx.fireChannelReadComplete();

                                ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
                                //构造处 http协议的返回体返回。
                                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                                //
                                ctx.channel().writeAndFlush(response);

                                ctx.channel().close();
                            }

                            /**
                             * 当channel不可用时调用， 不可用就是服务端 或者客户端断开连接，就会触发回调。
                             * @param ctx
                             * @throws Exception
                             */
                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("6. in - channelInactive 调用 当客户端主动发起关闭，就会回调.  如果是服务端主动发起的，也会被调用。");
                                ctx.fireChannelInactive();
                            }

                            //actic不可用后，调用这个。
                            @Override
                            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("7. in  - channelUnregistered 调用  channle移除掉了。");
                                ctx.fireChannelUnregistered();
                            }

                            /**
                             * 当连接通道关闭后就会回调，也就是tcp断开了。
                             * @param ctx
                             * @throws Exception
                             */
                            @Override
                            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("8. in  - handlerRemoved 调用");
                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                System.out.println("in - userEventTriggered 调用");
                                ctx.fireUserEventTriggered(evt);
                            }

                            @Override
                            public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("in - channelWritabilityChanged 调用");
                                ctx.fireChannelWritabilityChanged();
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                System.out.println("in - exceptionCaught 调用");
                                super.exceptionCaught(ctx, cause);
                            }
                        });

                        ch.pipeline().addLast(new ChannelOutboundHandler() {

                            //1.当channel可以register完成后，ss获取bind操作， socketchannel不会。
                            @Override
                            public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                                System.out.println("1. out  -- bind操作, socketChannel不会调用");
                                ctx.bind(localAddress, promise);
                            }

                            // 这个也不会调用，这个只是在BootStrap加的注册函数中调用。
                            @Override
                            public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                                System.out.println("2. out -- connect， 也不会调用吧");
                                ctx.connect(remoteAddress, localAddress, promise);
                            }

                            // handler 可以调用这个方法，执行完这个回调链路。
                            @Override
                            public void read(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("3. out --- read  --- read在channel 执行完active  或者 每次读完之后都回去被回调。意义是通知head开启新一轮的读。");
                                ctx.read();
                            }

                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                System.out.println("4. out  ---- write  -- 用户主动发起写操作 ");
                                ctx.write(msg, promise);
                            }

                            @Override
                            public void flush(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("5. out  -----  flush  --- 用户主动发起flush操作。");
                                ctx.flush();
                            }

                            @Override
                            public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                System.out.println("6 . out  ---- disconnect  channel断开会被调用。");
                                ctx.disconnect(promise);
                            }

                            @Override
                            public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                System.out.println("7. out  ---- close  ");
                                ctx.close(promise);
                            }

                            @Override
                            public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                                System.out.println("8 .out ----- deregister");
                                ctx.deregister(promise);
                            }

                            @Override
                            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("out --- handlerAdded");
                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("out ---- handlerRemoved");
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

                            }
                        });

                    }
                });
        ChannelFuture channelFuture = bootstrap.bind(9999).sync();
        channelFuture.channel().closeFuture().sync();

    }
}
