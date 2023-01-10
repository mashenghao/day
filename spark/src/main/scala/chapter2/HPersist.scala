package chapter2

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * spark rdd的持久化。
 * rdd读取文件之后组装后rdd，是需要消耗时间的，
 * 因此可以通过持久化rdd，直接使用rdd。对于shuffle算子，会直接被缓存的，
 * 为的就是怕数据丢失后，要进行重新计算。
 *
 *
 * rdd持久化后，
 *
 * @author: mahao
 * @date: 2020/12/29 23:29
 */
object HPersist {
  val conf = new SparkConf()
    .setMaster("local[3]")
    .setAppName("rdd的持久化")

  val sc = new SparkContext(conf)


  def main(args: Array[String]): Unit = {
    val rdd = sc.parallelize(1 to 9000000, 3)
    val rdd2: RDD[(Int, Int)] = rdd.map((e) => {
      //println("e" + e)
      (e * 2, 1)
    })
    val rdd22 = rdd2.filter(e => e._1 > 0)

    val rdd3: RDD[(Int, Int)] = rdd22.reduceByKey(_ + _)

    //1. 对于shuffle算子，则会自动进行缓存rdd3。所以rdd2的打印只会执行一次。
    //rdd3是shuffle算子，并collect调用了两次，rdd3使用了rdd2过来的，分析查看，
    //rdd3是被持久化了，因为rdd2只是执行了1次.如果不是shuffle算子，则结果不会被
    //自动持久化的，比如rdd被收集两次，则会进行两次遍历。
    rdd3.collect()
    println("////////////////")
    rdd3.collect()
    println("/////////////  shuffle  ///////////////////")

    //2.对于shuffle之前的算子，stage将会被重新执行到需要的rdd未知，比如上面的rdd22，会重新从序列中读取
    //数据，然后map，filter操作。如果rdd所在的stage过长，计算的代价比较大，可以将其持久化保存。
    //执行持久化后，之后的rdd的数据就不会被清除了，会被缓存到内存中。
    rdd2.persist(StorageLevel.MEMORY_AND_DISK_SER_2)

    rdd22.collect()
    println("......................")
    rdd22.collect()
    Thread.sleep(2000000)
  }
}
