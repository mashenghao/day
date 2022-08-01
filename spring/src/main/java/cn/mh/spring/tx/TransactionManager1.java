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
 * 事务管理器使用：
 *
 * //要求事务，有事务就用，没有就新建个事务
 * PROPAGATION_REQUIRED
 *
 * //挂起当前事务，新开事务运行。 新开事务，两个独立的connection，不会相互影响。
 * PROPAGATION_REQUIRES_NEW
 *
 * //事务嵌套，有事务嵌套运行，没事务就新开事务运行. 嵌套事务是会新建个savepoint，不会影响外部事务，外部事务会影响内部事务。
 * PROPAGATION_NESTED
 *
 * //强制要求有事务，没有事务报错
 * PROPAGATION_MANDATORY
 *
 * //有事务就用，没有不用事务
 * PROPAGATION_SUPPORTS
 *
 * //有事务挂起，以非事务运行
 * PROPAGATION_NOT_SUPPORTED
 *
 * //
 * PROPAGATION_NEVER
 *
 *
 * 1. 外层调用方法和内层被调用方法，有异常一起回滚，没问题一起提交。（共用一个事务）
 * 2. 内层被调用方法回滚与否，不会影响外层调用方法。而外层调用方法出异常回滚，也不会回滚内层被调用方法（两个独立的事务）
 * 3. 内层被调用方法回滚与否，不会影响外层调用方法。而外层调用方法出异常回滚，也会回滚内层被调用方法（嵌套事务）
 *
 * 1----->@Transactional(propagation=Propagation.REQUIRED) ：
 * 内外层方法共用外层方法的事务
 *
 * 2----->@Transactional(propagation=Propagation.REQUIRES_NEW) ：
 * 当执行内层被调用方法时，外层方法的事务会挂起。两个事务相互独立，不会相互影响。
 *
 * 3----->@Transactional(propagation=Propagation.NESTED) :
 * 理解Nested的关键是savepoint。他与PROPAGATION_REQUIRES_NEW的区别是，PROPAGATION_REQUIRES_NEW另起一个事务，将会与他的父事务相互独立，
 * 而Nested的事务和他的父事务是相依的，他的提交是要等和他的父事务一块提交的。也就是说，如果父事务最后回滚，他也要回滚的。
 *
 *
 * /////     原理     /////
 * 每个方法添加了事务，就对应一个TranstionStatus对象，如果方法是创建了connection，第一次进入事务，
 * 则事务中newTransaction 标记是否是新事物(第一方法)，newSynchronization也是第一个方法。
 * 之后的方法，获取事务，都是根据当前线程中有无事务connection，来判断的。
 * 如果有的话，就是复用当前事务， 没有的话，就去数据库申请连接，新开事务。
 * 如果是传播行为是NEW， 就需要用到suspend挂起资源，就会将当前事务挂起(记录当前事务的status，connection)，挂起资源保存到status中，
 * 然后清理掉线程中的资源， 表示当前线程已经无事务了，之后就会走上面的申请连接开启新事物的操作。
 *
 * 提交事务的原理，就是通过threadlocal中的connection，调用commit方法，就是提交事务了。 提交后，会将status中的complete设为true。 如果是事务套事务，则里面的啥也不用做，就是等最外面的事务status提交才会DB提交。**
 *
 * ////////   提交  /////////////
 *  每次事务提交，无论是内部事务还是最外部事物，都会调用 `	cleanupAfterCompletion` 方法，
 *  当是外层事务，就会清理掉绑定到线程中的资源， **子类实现 会有清理txObject与释放connection操作**  ，，，，**如
 *  果有挂起的资源，也会调用resume 进行恢复**
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
     *
     * 测试声明式事务: org.springframework.transaction.interceptor.TransactionInterceptor 该类是一个Advisor切面，
     * 通知advice就是对方法调用前后添加事务操作，操作就是transtionmanager的事务调用逻辑。
     */
    @Test
    public void testAnno() {

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void method1() {
        System.out.println("method1 执行");
    }
}
