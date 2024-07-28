package cn.mh.spring.inTx;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author mash
 * @date 2024/6/20 下午2:37
 */
public interface MyService2 {

    @Transactional
    void performTransactionalOperation();
    

}
