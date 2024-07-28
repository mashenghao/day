package pDesignPattern.promise;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import util.T;

/**
 *netty的promise设计
 *
 * @author mash
 * @date 2024/7/11 下午10:57
 */
public class Main {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        DefaultPromise<String> defaultPromise = new DefaultPromise<>(group.next());

        defaultPromise.addListener(new FutureListener<String>() {
            @Override
            public void operationComplete(Future<String> future) throws Exception {
                T.print(future.sync().get());
            }
        });

        T.print("设置开始");
        defaultPromise.setSuccess("success111");
        T.print("设置结束");

        group.shutdownGracefully();
    }
}
