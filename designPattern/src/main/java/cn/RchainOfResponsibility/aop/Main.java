package cn.RchainOfResponsibility.aop;

import java.util.Arrays;
import java.util.List;

/**
 * 参看FilterChain {@link javax.servlet.FilterChain}过滤器链和
 * spring的Aop链{@link org.springframework.aop.framework.ReflectiveMethodInvocation}实现的链式调用注册的责任链模式。
 *
 * @author mahao
 * @date 2022/08/01
 */
public class Main {

    public static void main(String[] args) {
        Handler hear = chain -> {
            System.out.println("车头1");
            chain.process();
            System.out.println("车头2");
        };
        Handler body = chain -> {
            System.out.println("车身1");
            chain.process();
            System.out.println("车身2");
        };
        Handler tail = chain -> {
            System.out.println("开始车尾");
            chain.process();
            System.out.println("结束车尾");
        };

        ChainObject chainObject = new ChainObject(Arrays.asList(hear, body, tail));

        hear.hand(chainObject);

    }


    interface Handler {

        void hand(ChainObject chain);

    }

    public static class ChainObject {

        private List<Handler> handlers;

        public ChainObject(List<Handler> handlers) {
            this.handlers = handlers;
        }

        private int i = 0;

        public void process() {
            if (i == handlers.size() - 1) {
                return;
            }

            //下一个
            i++;

            Handler handler = handlers.get(i);
            handler.hand(this);


        }
    }
}
