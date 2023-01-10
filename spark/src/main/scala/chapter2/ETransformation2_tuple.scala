package chapter2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子，关于tuple类型的数据进行操作
 * 对进行join操作,两个算子之间的曹组。对于3的操作，将会有3个stage，一是pairRdd1的读取，二是pairRdd2的读取，3是collect的操作，
 * 这是三个stage，中间的join是stage3的开始，是1和2的结果。
 *
 * @author: mahao
 * @date: 2020/12/27 22:47
 */
object ETransformation2_tuple {

  val conf = new SparkConf()
    //.setMaster("yarn")
    .setMaster("local[*]")
    .setAppName("ETransformation2_tuple")

  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    //1.直接创建pairRdd类型的rdd,智能是二元组，对三元组是不行的。
    val pairRdd: RDD[(Int, String)] = sc.parallelize(Seq((1, "zs"), (2, "ls"), (3, "ww")), 3)


    //2.执行tuple类型的算子操纵，直接按照key分组
    val groupRdd: RDD[(Int, Iterable[String])] = pairRdd.groupByKey(2)
    groupRdd.mapPartitionsWithIndex((i, it) => {
      println(i + "/////////////////////")
      while (it.hasNext) {
        print(it.next())
      }
      it
    }).collect()

    //3.对进行join操作,两个算子之间的曹组。对于3的操作，将会有3个stage，一是pairRdd1的读取，二是pairRdd2的读取，3是collect的操作，
    //这是三个stage，中间的join是stage3的开始，是1和2的结果。
    val pairRdd2: RDD[(Int, Int)] = sc.parallelize(Seq((1, 17), (2, 18), (3, 19)), 3)
    val joinRdd: RDD[(Int, (String, Int))] = pairRdd.join(pairRdd2)
    joinRdd.collect().foreach(println(_))


    val g1: RDD[(Int, Iterable[String])] = pairRdd.groupByKey()
    val g2: RDD[(Int, Iterable[Int])] = pairRdd2.groupByKey()
    val g3: RDD[(Int, (Iterable[String], Iterable[Int]))] = g1.join(g2)
    g3.foreach(e => {
      println("key:  " + e._1)
      println("v1: " + e._2._1.toList)
      println("v2: " + e._2._2.toList)
    })
    println("////////////////////////////////")
    //4. cogroup的算子操作,先对两个rdd进行分组操作，之后在进行合并操作。
    val cogroupRdd: RDD[(Int, (Iterable[String], Iterable[Int]))] = pairRdd.cogroup(pairRdd2)
    cogroupRdd.foreach(e => {
      println("key:  " + e._1)
      println("v1: " + e._2._1.toList)
      println("v2: " + e._2._2.toList)
    })


    Thread.sleep(200000)
  }
}
