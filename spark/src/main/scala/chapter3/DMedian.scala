package chapter3

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 有序数据中求中位数
 *
 * @author: mahao
 * @date: 2021/1/4 22:49
 */
object DMedian {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("DMedian中位数")
  val sc = new SparkContext(conf)


  def main(args: Array[String]): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 3)
    val sorted = rdd.sortBy(e => e).zipWithIndex().map {
      case (v, idx) => (idx, v)
    }
    sorted.foreach(println(_))
    val count = sorted.count()

    val median: Double = if (count % 2 == 0) {
      val l = count / 2 - 1
      val r = l + 1
      (sorted.lookup(l).head + sorted.lookup(r).head).toDouble / 2
    } else sorted.lookup(count / 2).head.toDouble
    println(median)
  }
}
