package cn.mh.spring;

import cn.mh.spring.inTx.MyService;
import cn.mh.spring.inTx.MyService2;
import cn.mh.spring.inTx.MyServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan("cn.mh.spring.webasync.servlet")
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

//        MyService myService = context.getBean(MyService.class);
//        MyService2 myService2 = context.getBean(MyService2.class);
//
//        // 执行事务操作
//        myService.performTransactionalOperation();
//        myService2.performTransactionalOperation();
//
//        context.close();
    }

}
