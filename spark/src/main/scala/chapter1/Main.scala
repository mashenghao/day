package chapter1

import org.apache.commons.collections.IteratorUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * spark的wordCount原理深度剖析。
 *
 * @author: mahao
 * @date: 2020/12/22 18:12
 */
object Main {
  def main(args: Array[String]): Unit = {

    //1. 初始化spark程序，比如scheduler等。
    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("wordCount")
    val sc = new SparkContext(sparkConf)

    sc.parallelize(List((1,List("a","b")),(2,List("c","d")))).flatMap(e=>{
      val it: Iterator[String] = e._2.iterator
//      val tuples: Iterator[(Int, String)] = it.map(a => (e._1, a))
      new Iterator[(Int,String)] {
        override def hasNext: Boolean = it.hasNext

        override def next(): (Int, String) = (e._1,it.next())
      }
    })
      .collect()
      .foreach(println(_))

    //2.spark的第一个算子，将hdfs上的文件，进行分割，分割成3个分区，组成spark的rdd。
    val lineRdd: RDD[String] = sc.textFile("file:///d:/tmp/log4j2.xml", 3)
    //3.spark程序的第二个算子，flatmap是将每个元素进行分割，分割成多个，返回的结果是一个序列。
    //这个操作，没有跨节点，都是和父rdd在同一个分区中进行计算，其实是同一个task。
    val flatRdd: RDD[String] = lineRdd.flatMap(e => e.split(" "))
    val mapRdd: RDD[(String, Int)] = flatRdd.map(e => (e, 1))
    //以上的三个rdd，组成了一个stage0,他们可以在同一个task中运行，并且分区可以并行运算，因为
    //之间的数据没有合并操作。这些操作没有走磁盘操作，他们直接都是窄依赖，stage0必须等待每一
    //task都运行结束后，才能执行下一个stage，运行完成后，会向driver汇报。driver

    // -==============//
    //下面的操作，是stage1，一共就两个stage,shuffle算子，作为下一个算子的开始，直到遇到下一个
    //宽依赖，这里的就是到达最后的行动算子了，共同组成了一个stage、
    val reduceRdd: RDD[(String, Int)] = mapRdd.reduceByKey((sum, num) => {
      println(sum + "  " + num)
      sum + num
    })

    mapRdd.distinct()
    /*
    这个地方有shuffle操作，这个算子是reduce操作，则shuffle时会在stage1开始时，在每个分区内，按照key进行排序，（不确定会不会进行reduce）,
    对相同的key，将数据放在一个，当进行reduce时，reduce也会有分区，默认是继承父类rdd的分区数，spark的reduce会从各个分区中拷贝分区中自己
    负责的key中的数据，然后对数据进行聚合操作。
     */

    val array: Array[(String, Int)] = reduceRdd.collect() //触发了job。
    array.foreach(println(_))
  }
}
