package cn.mh.spring.tx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务管理器使用
 *
 * @author mahao
 * @date 2022/07/23
 */
@Component
public class TransactionManager1 {

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 测试事务的使用
     */
    @Test
    public void testUser() {
        //1.获取事务的状态信息，这个status是用于控制传播行为和事务控制的，里面有上个事务挂起的资源 当前事务的信息
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        System.out.println("事务1开始执行  。。。。。 " + status);


        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status2 = transactionManager.getTransaction(definition);
        System.out.println("事务2 开始执行" + status2);
        transactionManager.commit(status2);
        System.out.println("事务2执行完毕 。。。。 ");

        transactionManager.commit(status);
        System.out.println("事务1执行完毕 。。。。 ");
    }

    /**
     * 测试声明式事务
     */
    @Test
    public void testAnno() {

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void method1() {
        System.out.println("method1 执行");
    }
}
