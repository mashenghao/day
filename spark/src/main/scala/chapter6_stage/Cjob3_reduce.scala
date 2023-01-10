package chapter6_stage

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 *
 * @author: mahao
 * @date: 2021/1/25 15:02
 */
object Cjob3_reduce {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("Cjob3_reduce")

    val sc = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(Seq(1, 2, 4, 5, 6), 2)
    val mapRDD: RDD[Int] = rdd.map(e => e * 2)
    val partitionRDD: RDD[Int] = mapRDD.coalesce(3, true)
    val sum: Int = partitionRDD.reduce((a, b) => a + b)
    println(sum)

    Thread.sleep(9999999)
  }
}
