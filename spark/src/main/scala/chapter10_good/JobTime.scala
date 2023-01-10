package chapter10_good

import org.apache.spark.{SparkConf, SparkContext}

/**
 * JOB触发的顺序，会不会被等待
 *
 * @author: mahao
 * @date: 2021/11/02
 */
object JobTime {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf()
      .setMaster("local[8]")
      .setAppName("ShuffleRDD调用两次,reduce会去拉取几次数据")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(Seq(1, 2, 3, 4), 2)

    val array = rdd.mapPartitions(it => {
      Thread.sleep(10000)
      it
    }).collect()
    println("////////////////////////////")

    println()
  }
}
