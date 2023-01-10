package chapter4

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}

/**
 *1.spark源码-sparkContext的创建
 * sparkContext在创建的时候,会创建三个组件,taskScheduler,DagScheduler,SparkUI。
 * 1.taskScheduler组件创建过程：
 * SparkContext.createTaskScheduler(this, master) --> new TaskSchedulerImpl(sc)/new SparkDeploySchedulerBackend(scheduler, sc, masterUrls) -->
 * scheduler.initialize(backend) --> schedulableBuilder.buildPools() --> taskScheduler.start()
 * -->  backend.start() --> SparkDeploySchedulerBackend.start() --> client.start() --> actor = actorSystem.actorOf(Props(new ClientActor))
 * --> registerWithMaster() --> tryRegisterAllMasters() --> actor ! RegisterApplication(appDescription)
 * 向spark master的注册，就是想spark的master发送一个appDescription。
 * 2.DAGScheduler，DAGScheduler是面向stage的高层次的调度层，他为每个job计算一个以stage
 * 为划分的DAG图(有向无环图)，追踪rdd和stage的输出是否被物化了，优化task的最优调度策略。
 * 将stage作为taskSet提交到底层的taskScheduler，并在集群上运行他们。 除了处理stage的dag，
 * 也负责决定task的的运行的最佳位置，基于当前缓存的状态，讲这些状态提交给taskSchedulerImpl。
 * 此外，还负责shuffle丢失的失败，就的stage将会被重新提交。 stage内部的task不是由于shuffle丢失导致的，
 * 则解决鬼taskScheduler进行重试尝试。
 * 3.sparkui
 *
 * @author: mahao
 * @date: 2021/1/2 16:01
 */
object ASparkContext {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("sparkContext创建")
    val sc = new SparkContext(conf)

    val rdd = sc.makeRDD(1 to 10, 3)
    val i: Int = rdd.reduce(_ + _)
    rdd.collect()
    println(i)
  }
}
