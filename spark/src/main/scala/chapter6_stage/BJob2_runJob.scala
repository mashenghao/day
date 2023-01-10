package chapter6_stage

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * job触发流程2，运行job分析：stage的理解在{@link Ajob}
 *
 * @author: mahao
 * @date: 2021/1/24 22:38
 */
object BJob2_runJob {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("BJob2_runJob")
    val sc = new SparkContext(conf)

    val fileRDD: RDD[String] = sc.textFile("input/chapter1/word.txt")
    val flatRDD: RDD[String] = fileRDD.flatMap(e => e.split(" "))
    val pairRDD: RDD[(String, Int)] = flatRDD.map(e => (e, 1))
    val reduceRDD: RDD[(String, Int)] = pairRDD.reduceByKey((x, i) => x + i).repartition(5)
    val maprdd2: RDD[(Int, String)] = reduceRDD.map((e) => (e._2, e._1)).cache()
    maprdd2.foreach(println(_))
    pairRDD.coalesce(1)
  }
}
