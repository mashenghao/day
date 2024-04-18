package cn.mh.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * webAsyncTask使用测试
 *
 * @author mash
 * @date 2024/4/18 上午9:30
 */
@RestController
public class WebAsyncTaskController {


    @GetMapping("/test1")
    public WebAsyncTask<String> test1() {

        System.out.println(Thread.currentThread().getName() + " - test1");

        WebAsyncTask<String> task = new WebAsyncTask<>(() -> {
            sleep(5000);
            System.out.println(Thread.currentThread().getName() + " - callable");
            return "aaa";
        });
        task.onCompletion(() -> {
            System.out.println(Thread.currentThread().getName() + " - 任务完成");
        });

        return task;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/test2")
    public String test2() {
        return "aa2";
    }
    
}
