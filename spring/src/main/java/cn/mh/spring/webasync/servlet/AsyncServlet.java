package cn.mh.spring.webasync.servlet;

import cn.mh.spring.webasync.WebAsyncTaskController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author mash
 * @date 2024/4/21 下午10:49
 */
@WebServlet(value = "/asyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logStr("主线程执行");
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(1000);
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                logStr("onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                logStr("onTimeout");
                asyncContext.getResponse().getWriter().write("time out");
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
            logStr("异步任务开始执行");
            WebAsyncTaskController.sleep(3000);
            try {
                asyncContext.getResponse().getWriter().write(UUID.randomUUID().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logStr("异步任务写出数据");

            //当任务结束时，这里调用complete告诉容器做清理操作。
            asyncContext.complete();
            logStr("异步任务结束");
        });
        logStr("主线程结束");


    }

    public static synchronized void logStr(String str) {
        System.out.println(Thread.currentThread().getName() + "  :  " + str);
    }


}
