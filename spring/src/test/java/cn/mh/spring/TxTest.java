package cn.mh.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author mahao
 * @date 2022/07/23
 */
@SpringBootTest
public class TxTest {

    @Autowired
    private PlatformTransactionManager transactionManager;



}
