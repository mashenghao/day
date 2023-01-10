package chapter13_scheduler

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/** 广播变量测试
 *
 * @author: mahao
 * @date: 2021/12/05
 */
object BorcastVar {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("测试广播变量的实现逻辑")

    val sc = new SparkContext(sparkConf)

    val brI: Broadcast[Int] = sc.broadcast(1)


    val rdd: RDD[Int] = sc.parallelize(Seq(1, 2, 3, 4, 5, 6), 2)
    val array = rdd.map(e => {
      brI.value * e
    }).foreach(println(_))

    val value = sc.accumulator(1)

    value.add(1)

    value.value



  }
}
