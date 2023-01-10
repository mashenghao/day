package chapter2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * spark的算子：
 * spark的算子类型分为两种，转换算子和行为算子，转换算子之间的操作，不会
 * 真正触发程序的执行，只是记录执行算子之间数据变换的逻辑，当遇到行为算子
 * 的时候，才会去真正执行任务。
 * 转换算子：
 * map,reduceByKey
 * 其中转换算子，也有一些特殊的操作，只是针对Tuple2的算子，比如reduceByKey，groupByKey。
 * 需要隐式导入spark的相关隐式转换，之后就会将Rdd变为PairRDDFunctions，然后就会有先关的操作了。
 *
 * 行为算子：
 * collect,reduce等操作。
 * <p>
 * 下面的这些程序都是在Driver端运行的，也就是在自己的电脑上执行spark的程序，
 * 但是在action执行之前，trans算字都是lazy的，只有执行到action的时候，才会
 * 将调用DagScheduler去执行程序。一个行动算子就是一个job，触发到了job就回去执行程序，
 * 这个job之间的算子操作逻辑就是一个Dag调度图，然后dag调度器会分析这个dag图，查看哪些
 * 算子之间的操作是可以并行的，其实就是查找窄依赖，或者说是寻找shuffle算子，在他之前的
 * 算子可以共同组成一个stage，这些stage逻辑将会被发送到executor中创建的task中去运行。
 * dagScheduler中有TaskScheduler，在job在提交后，dag分析后，会将同一个stage中
 * 任务分成taskSet，将这些taskset分给executor中的task去运行。
 * <p>
 * <p>
 * 有几个问题，1是数据源是如何分配到每个分区的，如何传输的? 2是task是如何知道计算逻辑的？
 *
 * @author: maHao
 * @date: 2020/12/26 21:52
 */
object BRdd {

  val conf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("rdd_create")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    //1. 转换算子
    val rdd: RDD[Int] = sc.makeRDD(Seq(1, 2, 3), 3)

    rdd.map((_, 1))
      .reduceByKey((a, b) => 1 + 1) //他是转换算子
      .groupByKey(3)
    //2. 行为算子
    //这个reduce操作，会触发之前所有的转换算子的操作，会将操作拆分成多个task到多个机器
    //上去并行执行，reduce算子，会现在本地reduce进行聚合，之后会进行一次全局的聚合，
    //然后将其结果返回给Driver程序。
    rdd.reduce(_ + _) //这个是行为算子，应为他的返回值是一个数字，而不是rdd。

  }
}
