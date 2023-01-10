package chapter10_good

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * JVM调优的方式：
 * 1.spark.storage.memoryFraction 默认0.6，表示executor内存6的内存用于缓存分区数据，4的内存用于task运行中处理的对象。
 *
 * @author: mahao
 * @date: 2021/3/14 22:35
 */
object CJvm {
  val sparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("CJvm")
    .set("spark.executor.memory", "256M")
    .set("spark.driver.memory", "256M")

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(sparkConf)
    val aa = List("11s")
    val aaBro: Broadcast[List[String]] = sc.broadcast(aa)
    val lineRdd: RDD[String] = sc.textFile("hdfs://node01:9000/tmp/index-all.html", 2)
    val flatRdd: RDD[String] = lineRdd.flatMap(e => e.split(" "))
    val mapRdd: RDD[(String, Int)] = flatRdd.map(e => (e, 1)).persist(StorageLevel.DISK_ONLY)
    val reduceRdd: RDD[(String, Int)] = mapRdd.reduceByKey((sum, num) => {
      sum + num
    })
    reduceRdd.foreach((e) => {

    })

    Thread.sleep(1000000)
  }
}
