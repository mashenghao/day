package cn.mh.spring.inTx;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 *
 * 实际测试下来，无论是接口 还是 类上
 *
 * @author mash
 * @date 2024/6/20 下午2:14
 */
@Service
public class MyServiceImpl2 implements MyService2 {

    @Override
    public void performTransactionalOperation() {

        // 检查当前方法是否在事务中
        boolean isInTransaction = TransactionSynchronizationManager.isActualTransactionActive();

        if (isInTransaction) {
            System.out.println("当前方法在事务中执行");
        } else {
            System.out.println("当前方法不在事务中执行");
        }

        // 具体的业务逻辑
        System.out.println("Performing transactional operation");
    }
}

