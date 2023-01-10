package chapter3

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * topN案例：
 * 1. 对文本文件内的数字，去最大的前三个
 * 2.取出每个班级中全三名的学生信息
 *
 * @author: mahao
 * @date: 2021/1/1 22:46
 */
object CTopN {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("topN")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    //1.1取出出现次数最多的三个单词
    val fileRDD: RDD[String] = sc.textFile("input/chapter1/word.txt", 3)
    val topNum: Array[(String, Int)] = fileRDD
      .flatMap(_.split(" "))
      .map(e => (e, 1)) //stage0
      .reduceByKey(_ + _)
      .sortBy(e => e._2, false) //stage1  //按照出现次数，降序排序
      .take(3) //stage2 //去取前三位
    println("前三位数字是： " + topNum.toList)


    //2. 按照班级中成绩进行排序
    val fileRDD2: RDD[String] = sc.textFile("input/chapter1/sort.txt", 3)
    val mapRdd: RDD[(String, Int)] = fileRDD2.map(e => {
      val ss: Array[String] = e.split(" ")
      (ss(0), ss(1).toInt)
    })
    //按照班级进行分组
    val groupRdd: RDD[(String, Iterable[Int])] = mapRdd.groupByKey()
    //分别获取每一组中的前三名
    val take3: RDD[(String, Array[Int])] = groupRdd.map(e => {
      val array: Array[Int] = e._2.toArray
      array.sortBy(e => e)
      (e._1, array.take(3))
    })
    take3.foreach(e => println(e._1 + e._2.toList))

    Thread.sleep(999999)
  }
}
