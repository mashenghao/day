package cn.mh.spring.webasync;

import org.assertj.core.util.Maps;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.Map;
import java.util.UUID;

import static cn.mh.spring.webasync.servlet.AsyncServlet.logStr;
import static org.springframework.web.context.request.async.CallableProcessingInterceptor.RESULT_NONE;

/**
 * webAsyncTask使用测试：
 * <p>
 * <p>
 * 使用的执行线程梳理：
 * <p>
 * 1.标准的mapper访问流程链路：
 * http-nio-8001-exec-1  UserFilter before
 * http-nio-8001-exec-1HandlerInterceptor preHandle
 * http-nio-8001-exec-1 主线程 进入
 * http-nio-8001-exec-1主线程 结束
 * http-nio-8001-exec-1  UserFilter after
 * <p>
 * 2.使用自己的线程去执行异步任务
 * 异步任务webAsync-1 -异步任务运行
 * <p>
 * 3. 使用nio线程处理返回，  发现只会处理spring的组件，没有tomcat的filter。
 * http-nio-8001-exec-3HandlerInterceptor preHandle  会执行2次，因为判断异步任务是通过返回值进行判定的
 * http-nio-8001-exec-3HandlerInterceptor postHandle  第一次执行
 * http-nio-8001-exec-3HandlerInterceptor afterCompletion
 * http-nio-8001-exec-3 异步任务完成
 *
 * @author mash
 * @date 2024/4/18 上午9:30
 */
@Controller
@RequestMapping("/webAsync")
public class WebAsyncTaskController {

    /**
     * 普通的请求就是：
     * <p>
     * 1.客户端发送请求
     * 2.tomcat等容器处理Filter责任链，处理通过的请求找到对应的servlet去执行
     * 3.DispatchServlet处理所有的请求，先去执行Interceptor链的preHandler，处理不通过，则不执行了
     * 4.之后交给mapper 去controller执行，执行结束判断返回的返回值，是ModelView 还是 @ResponseBody(RequestResponseBodyMethodProcessor) WebAsyncTask，都有对应的HandlerMethodReturnValueHandler
     * 去处理controller的返回值，普通的就是获取HttpMessageConverter去转为JSON串写出到response中
     * 5.之后返回的modelView就是null
     * 6.成功则去去执行Interceptor链的postHandler,  抛出异常或者成功都去执行afterCompleteHandler
     * 7.之后执行filter的后处理方法
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/test2")
    public Map<String, String> test2() {
        return Maps.newHashMap("aa", "bv");
    }

    /**
     * 普通的请求就是：
     * <p>
     * 1.客户端发送请求
     * 2.tomcat等容器处理Filter责任链，处理通过的请求找到对应的servlet去执行
     * 3.DispatchServlet处理所有的请求，先去执行Interceptor链的preHandler，处理不通过，则不执行了
     * 4.之后交给mapper 去controller执行，执行结束判断返回的返回值，是ModelView 还是 @ResponseBody(RequestResponseBodyMethodProcessor) WebAsyncTask，
     * 都有对应的HandlerMethodReturnValueHandler去处理controller的返回值，普通的就是获取HttpMessageConverter去转为JSON串写出到response中
     * <p>
     * <p>对别普通的请求，WebAsyncTask的特别是多了个特殊的returnValue的返回处理AsyncTaskMethodReturnValueHandler去处理的。
     * <p> 查看如何实现异步执行的，以及如何在分发进dispater中的。  ----还是调用的容器的支持。
     * 处理逻辑是 spring对WebAsyncTask的返回值，会用task定义的线程池提交真实的任务,然后当前这次请求就结束了，但是response中没有任何返回写回客户端。
     * 当异步任务call()执行完成后，就会将结果放到request的属性中， 然后重新调用容器的dispatch方法，这次请求就会重新进入到请求链路中了，但是不会去
     * 执行filter这些方法了。  第二次进入判断WebAsyncTask返回，也是AsyncTaskMethodReturnValueHandler 他会判断request的属性中是否已经计算好了，
     * 是的话，就直接返回了。
     * <p>
     *    todo： 既然是容器做的异步，为啥filter为啥不二次执行？
     *    org.springframework.web.context.request.async.StandardServletAsyncWebRequest#dispatch()
     *    javax.servlet.AsyncContext#dispatch()
     * <p>
     * <p>
     * <p>
     * 5.之后返回的modelView就是null
     * 6.成功则去去执行Interceptor链的postHandler,  抛出异常或者成功都去执行afterCompleteHandler
     * 7.之后执行filter的后处理方法
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/test1")
    public WebAsyncTask<?> test1() {
        AsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("异步任务webAsync-");

        System.out.println(Thread.currentThread().getName() + " 主线程 进入");

        WebAsyncTask<Object> task = new WebAsyncTask<>(2000L, taskExecutor, () -> {

            sleep(3000);
            logStr(Thread.currentThread().getName() + " -异步任务运行");
            sleep(5000);
            logStr(Thread.currentThread().getName() + " -异步任务结束");
            return UUID.randomUUID().toString();
        });
        task.onCompletion(() -> {
            logStr(Thread.currentThread().getName() + " 异步任务完成");
        });
        task.onTimeout(() -> {
            logStr("on timeout 调用");
            return "我是结果222";
        });
        task.onError(() -> {
            logStr("on onError 调用");
            return RESULT_NONE;
        });

        System.out.println(Thread.currentThread().getName() + "主线程 结束");

        return task;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " 中断了");
        }
    }


}
