package chapter2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子1，map算子，filter算子与flatMap算子。
 *
 * @author: mahao
 * @date: 2020/12/27 21:02
 */
object DTransormation1 {

  val conf = new SparkConf()
    //.setMaster("yarn")
    .setMaster("local[*]")
    .setExecutorEnv("executor-memory", "1g")
    .setExecutorEnv("executor-cores", "1")
    .set("driver-memory", "1g")
    .set("spark.executor.memory", "1g")
    .setAppName("rdd_create")

  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    //1. 行为算子，创建初始rdd，使用的类是ParallelCollectionRDD
    val numRdd: RDD[Int] = sc.makeRDD(Seq(1, 2, 3, 4, 5, 6), 3)
    //2.map算子，对rdd中的每个元素，进行计算，这个是
    // MapPartitionsRDD在各自的分区中进行的计算。
    val mapRdd: RDD[Int] = numRdd.map(e => e * 2)
    //3.filter,是对数据集合进行过滤，返回结果为true的使用。MapPartitionsRDD,这个的rdd和
    //map算子的rdd是同一个rdd，使用的是不同的方法而已。
    val filterRdd: RDD[Int] = mapRdd.filter(e => e % 2 == 0)

    filterRdd.foreach(println(_))


    //4. mapPartitions算子，该算子参数是每个分区中的数据，批量对分区中的数据进行操作。
    //这个和map的算子做比较，发现是不一样的。
    val mapPartitionsRdd: RDD[Int] = numRdd.mapPartitions(nums => {
      //此时的参数nums是一个迭代器，代表着分区中的所有数据。
      nums
    })
    println("mapPartitions算子： " + mapPartitionsRdd.collect().toList)


    Thread.sleep(200000)
  }
}
