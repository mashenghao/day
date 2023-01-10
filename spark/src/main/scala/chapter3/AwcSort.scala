package chapter3

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 案例：基于排序机制实现wordcount
 * 实现单词次数按照出现次数降序排序,使用sortbykey则是按照
 * key的compare方法进行排序的。
 *
 * @author: mahao
 * @date: 2021/1/1 21:56
 */
object AwcSort {

  val conf: SparkConf = new SparkConf()
    //.setMaster("local[*]")
    .setMaster("spark://192.168.80.11:7077")
    .setAppName("AwcSort")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    val fileRDD: RDD[String] = sc.textFile("input/chapter1/word.txt")
    val reduceRdd: RDD[(String, Int)] =
      fileRDD.flatMap(_.split(" "))
        .map(e => (e, 1))
        .reduceByKey(_ + _)
    //将reduce后的结果按照出现的次数进行降序排序,这里指定了分区的个数，如果不指定
    //将只会在分区内是排序的。也可以使用collect进行排序。
    val sortRdd: RDD[(Int, String)] = reduceRdd.map(e => (e._2, e._1)).sortByKey(numPartitions = 1)
    val sortRdd2: RDD[(String, Int)] = reduceRdd.sortBy(e => e._2)
    sortRdd.collect().foreach(println(_))
  }
}
