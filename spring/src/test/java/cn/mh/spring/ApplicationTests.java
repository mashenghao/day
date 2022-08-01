package cn.mh.spring;

import cn.mh.spring.tx.TransactionManager1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private TransactionManager1 transactionManager1;

    @Test
    void contextLoads() {
        System.out.println(transactionManager1);
    }

}
