package chapter10_good

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

/**
 * ShuffleRDD被调用两次，shuffle会被触发几次。
 *
 * @author: mahao
 * @date: 2021/11/02
 */
object ReduceTwo {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("ShuffleRDD调用两次,reduce会去拉取几次数据")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5, 6), 2)

    val pairRDD = rdd.map(e => (e % 2, e))

    val shuffleRDD = pairRDD.reduceByKey((x, y) => x + y).persist()
    shuffleRDD.cache()
    shuffleRDD.checkpoint()

    val array = shuffleRDD.map(e => e._2).collect()

    println("////////////////////////////////////////////////////////////////////" + array)

    val ints = shuffleRDD.map(e => e._2).collect()
    println(ints)
  }
}
