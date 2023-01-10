package chapter13_scheduler

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * dagscheduler 看如何划分job。
 *
 * @author: mahao
 * @date: 2021/11/19
 */
object DagSchedulerTest {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("ShuffleRDD调用两次,reduce会去拉取几次数据")
      .set("spark.logLineage", "true")

    val sc = new SparkContext(sparkConf)

    val rdd1: RDD[Int] = sc.parallelize(Seq(1, 2, 3, 4, 5, 6), 2)

    val rdd2: RDD[Int] = rdd1.map(e => e + 1)

    val rdd3: RDD[Int] = rdd2.repartition(3)

    val rdd4: RDD[Int] = rdd3.map(e => e - 1)

    val array = rdd4.collect()

    val size = rdd4.count()

    println("大小是:" + array)
    println("数量是:" + size)

  }
}
