package oChannelHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelPipeline;

/**
 * ChannelPipLine的链路：
 *
 * 1、 理解：
 * //对应netty的ChannelHandler
 * public interface Filter {
 * 			void doFilter(ServletRequest request, ServletResponse response, FilterChain chain);
 * }
 *
 *
 * //对应netty的ChannelContext
 * public interface FilterChain {
 *      void doFilter(ServletRequest request, ServletResponse response) ;
 * }
 *
 * //tomcat的数组对应channelPipline。
 * private ApplicationFilterConfig[] filters = new ApplicationFilterConfig[0];
 *
 *
 * Filter的链式调用实现逻辑放在了FilterChain中，
 *   先调用第一个filter的doFilter方法执行业务，
 *   内部会调用FilterChain的doFilter方法进行第二个filter的跳转， 如果把FilterChain#doFilter 改成fireDoFilter就和netty一致理解了。
 *
 *2. 问题：
 *
 * 2.1 : ChannelHandlerContext 为什么要继承两个接口 ChannelOutboundInvoker 和 ChannelInboundInvoker。
 * 理解是因为 ChannelHandler有 read 和 write两大类型的回调。  write类型的回调ChannelOutboundInvoker除了有context 还有channel去触发， Channel又不能触发read操作，所以这个就拆分开来了。
 *
 * ChannelHandler 又分成了两个大类，
 * 			ChannelInboundHandler  对应 ChannelInboundInvoker这个上下文
 * 			ChannelOutBoundHandler  对应 ChannelOutBoundHandler上下文
 *
 *
 *
 * 2.2: ：：：：重要：：：：：： &&&&&&&&&&&  ChannelContext 如何找到下一个实现了这个回调接口的Handler的。
 *        比如实现了ChannelInBoundHandler ，在里面调用write方法，则要一直找到实现了write的ChannelOutBoundHandler。
 *
 *      创建Context，会计算出来这个Handler实现了那些ChannelHandler的回调接口，实现方式是通过将其saun
 *
 * @author mahao
 * @date 2022/12/15
 */
public class PipLine {

    public static void main(String[] args) {

        DefaultChannelPipeline defaultChannelPipeline;
        ChannelHandler channelHandler;
        /**
         * {@link  io.netty.channel.AbstractChannelHandlerContext#executionMask}
         */

        ChannelHandlerContext context = null;

    }
}
