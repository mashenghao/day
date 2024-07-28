package cn.mh.spring.webasync.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static cn.mh.spring.webasync.WebAsyncTaskController.sleep;
import static cn.mh.spring.webasync.servlet.AsyncServlet.logStr;

/**
 * @author mash
 * @date 2024/4/23 下午1:57
 */
@WebServlet(value = "/asyncDispatch", asyncSupported = true)
public class AsyncDispatchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logStr("doGet被执行: " + req);
        if (req.getDispatcherType() == DispatcherType.ASYNC) {
            //第二次执行这里获取值 写出执行完毕
            logStr("写出结果开始");
            resp.getOutputStream().write(req.getAttribute("Result").toString().getBytes());
            return;
        } else {
            //第一次先执行这里，设置值，然后当前执行结束， 执行完毕后，重新进入分发
            AsyncContext asyncContext = req.startAsync(req, resp);
            asyncContext.setTimeout(50000);
            asyncContext.addListener(new AsyncListener() {
                @Override
                public void onComplete(AsyncEvent event) {
                    logStr("onComplete");
                }

                @Override
                public void onTimeout(AsyncEvent event) {
                    logStr("onTimeout");
                    asyncContext.getRequest().setAttribute("Result", "我是超时结果");
                    asyncContext.dispatch(); //分发也不会报错

                    sleep(5000); //这里必须等待执行结束后，才会去分发，因为这里是标记的这个task的状态， 执行完成后，才能被下次轮训调用了
                    logStr("onTimeout  end");
                }

                @Override
                public void onError(AsyncEvent event) throws IOException {
                    logStr("onError");
                }

                @Override
                public void onStartAsync(AsyncEvent event) throws IOException {
                    logStr("onStartAsync");
                }
            });

            asyncContext.start(() -> {
                sleep(200000);

                logStr(" 异步执行");

                //将结果设置到请求里面
                asyncContext.getRequest().setAttribute("Result", "我是结果");

                //再次进行分发
                asyncContext.dispatch();
                sleep(100000);
                logStr("call结束: " + req);
            });
            asyncContext.getRequest().setAttribute("Result", "我是main结果");
            //这里执行分发，dispatch后的执行也是当前线程，只有在asyncContext.start(）中执行，才会用其他的线程处理。  我理解是
            //asyncContext.dispatch()会改变asyncContext的状态, tomcat在service方法执行完成后，轮训状态，看看是否需要执行，因为改了状态需要执行，所以就是同一个线程了。
            asyncContext.dispatch();

            sleep(10000);
            logStr("doGet执行结束: " + req);
        }
    }
}
