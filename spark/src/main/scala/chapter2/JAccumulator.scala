package chapter2

import org.apache.spark.rdd.RDD
import org.apache.spark.{Accumulator, SparkConf, SparkContext}

/**
 * 累加器：
 * 1.累加器是在task端只能增加，在driver端汇总，累加器具有分布式计数的能力。
 * 累加器在driver端声明，各个task中可以对这个变量进行累加，但是不能获取值，
 * 每个task都有这个变量的副本，更改的值是副本的值。
 *2. 累加器在转换算子中，如果算子被多次执行，则累加器的值也会多次计算，累加器保证
 * 每个task内部只更新一次，即使task失败重启，也不会在更新重试。在转换操作时，必须意识到
 * 作业的重新调度会造成累加器的多次更新。
 *3.累加器同样具有Spark懒加载的求值模型。如果它们在RDD的操作中进行更新，它们的值只在RDD
 * 进行行动操作时才进行更新。
 * {@url https://www.cnblogs.com/itboys/p/11056758.html}
 *
 * @author: mahao
 * @date: 2020/12/30 19:03
 */
object JAccumulator {
  val conf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("累加器")

  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    //1.创建一个累加器
    val counter: Accumulator[Int] = sc.accumulator(1, "counter")
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 3)
    val partRdd: RDD[Int] = rdd.mapPartitions(it => {
      counter.add(1)
      it
    })
    println("第一次获取counter值是：" + counter.value)
    partRdd.foreach(e => print(e + " "))
    println("第二次获取counter值是：" + counter.value)
    partRdd.foreach(e => print(e + " "))
    println("第三次获取counter值是：" + counter.value)

    Thread.sleep(9999999)
  }
}
