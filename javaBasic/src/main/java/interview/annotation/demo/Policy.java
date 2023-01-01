package interview.annotation.demo;


/**
 * aop中未获取到分布式锁的时候，如何处理执行
 *
 * @author mahao
 * @date 2022/12/30
 */
public enum Policy {
    /**
     * 忽略处理，啥也不做
     */
    IGNORE {
        @Override
        public Object handle(String lockId, String methodName) {
            return null;
        }
    },
    /**
     * 忽略处理，打印个info日志
     */
    INFO {
        @Override
        public Object handle(String lockId, String methodName) {
//            log.info("方法[{}]未竞争到分布式锁[{}],目标方法忽略执行", methodName, lockId);
            return null;
        }
    },
    /**
     * 抛出个UnGetDisLockException给外部，外部可以捕获去处理。
     */
    EXCEPTION {
        @Override
        public Object handle(String lockId, String methodName) {
            throw new RuntimeException(lockId);
        }
    };

    /**
     * 回调接口
     *
     * @param lockId
     * @param methodName
     * @return
     */
    public abstract Object handle(String lockId, String methodName);
}
