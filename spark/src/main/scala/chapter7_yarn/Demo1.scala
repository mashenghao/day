package chapter7_yarn

/**
 * spark on yarn 模式，在本地启动的主程序是 org.apache.spark.deploy.yarn.Client，
 * 1. 向RM注册application，申请container，用于启动applicationMaster。
 * 2. RM收到Client注册，获取参数amClass，获取container启动的主函数，am的是 org.apache.spark.deploy.yarn.ApplicationMaster
 * 3. ApplicationMaster在集群的container中启动后，am新开线程启动driver程序，从而启动用户主程序。
 * 4. am主线程中去RM申请container并启动executor，向NM发送启动container的命令，命令的主启动类是 org.apache.spark.executor.CoarseGrainedExecutorBackend
 * 5. 之后就是spark executor与driver之间的调度操作了。
 *
 * @author: mahao
 * @date: 2021/2/20 17:24
 */
object Demo1 {

}
