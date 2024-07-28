package mEventLoop;

import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 第一层： JDK Executor标准设计
 *
 * 第二层： EventExecutorGroup 具备next一个EventExecutor 和迭代的能力
 *
 * 第三层： EventLoopGroup具备注册channel和next一个EventLoop的能力
 *
 *
 *
 * @author mash
 * @date 2024/7/14 16:46
 */
public class EvnetLoopGtoupStruct {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);


    }
}
