package chapter1

import java.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
 * java 和 scala版本的hello程序，先以本地模式运行
 *
 * @author: mahao
 * @date: 2020/12/13 16:14
 */
class Hello {

  @Test
  def demo23(): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("wordCount")

    val sc = new SparkContext(sparkConf)
    val javaSc = new JavaSparkContext(sc)
    val list = new util.ArrayList[String]()
    //    list.add("a")
    //    list.add(null)
        list.add("c")
    val lineRdd: RDD[String] = javaSc.parallelize(list)

    val outPutPath = "D:/tmp/b4";
    lineRdd.saveAsTextFile(outPutPath+".tmp")

    //    val conf = new Configuration()
    //    conf.set("fs.defaultFS","hdfs://10.100.2.100")
    val hdfs: FileSystem = FileSystem.get(sc.hadoopConfiguration)
    FileUtil.copyMerge(hdfs, new Path(outPutPath + ".tmp"), hdfs, new Path(outPutPath), true, sc.hadoopConfiguration, null)
  }

  @Test
  def demo1(): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("wordCount")

    val sc = new SparkContext(sparkConf)
    val lineRdd: RDD[String] = sc.textFile("input/chapter1/word.txt")
    val flatRdd: RDD[String] = lineRdd.flatMap(e => e.split(" "))
    val mapRdd: RDD[(String, Int)] = flatRdd.map(e => (e, 1))
    val reduceRdd: RDD[(String, Int)] = mapRdd.reduceByKey((sum, num) => sum + num)
    reduceRdd.foreach(e => {
      println(e._1 + "  " + e._2)
    })
  }

  //使用spark集群来运行
  @Test
  def demo2(): Unit = {
    val sparkConf = new SparkConf()
      //.setMaster("spark://192.168.80.11:7077")
      .setMaster("local[*]")
      .setAppName("wordCount")

    val sc = new SparkContext(sparkConf)
    val lineRdd: RDD[String] = sc.textFile("input/chapter1/word.txt")
    val flatRdd: RDD[String] = lineRdd.flatMap(e => e.split(" "))
    val mapRdd: RDD[(String, Int)] = flatRdd.map(e => (e, 1))
    val reduceRdd: RDD[(String, Int)] = mapRdd.reduceByKey((sum, num) => sum + num)
    reduceRdd.foreach(e => {
      println(e._1 + "  " + e._2)
    })

    Thread.sleep(20000000)
  }
}
