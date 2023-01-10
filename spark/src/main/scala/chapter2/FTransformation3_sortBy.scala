package chapter2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子3，对普通数据类型进行排序和分组等操作。
 * 通过传入一个函数，对函数返回的不同结果值，将判断key的是都不同。
 *
 * @author: mahao
 * @date: 2020/12/27 22:47
 */
object FTransformation3_sortBy {

  val conf = new SparkConf()
    //.setMaster("yarn")
    .setMaster("local[*]")
    .setAppName("ETransformation3_sortBy")

  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    val numRdd: RDD[Int] = sc.parallelize(Seq(1, 2, 3, 4, 5, 6), 3)
    //groupBy算子，将元素按照函数的结果值进行分组，返回值按照
    //    分组逻辑划分，案例是按照奇偶划分，按照0 1 进行分组。 groupBy的返回
    //    元素是元组，_1是分组值，后面可迭代序列是值。
    val groupRdd: RDD[(Int, Iterable[Int])] = numRdd.groupBy(e => {
      e % 2
    })
    //glom的操作，是将每个分区中的数据，保存到一个集合中，rdd的类型是Array[T],T是原rdd的类型。
    val rdd: RDD[Array[(Int, Iterable[Int])]] = groupRdd.glom()
    //    //上面的操作，相当于map将结果收集起来,这个的操作，没法获取分区，所以是假实现。
    val rdd2: RDD[Array[(Int, Iterable[Int])]] = groupRdd.map(e => {
      Array((e._1, e._2))
    })
    rdd2.collect()
    Thread.sleep(200000)
  }
}
