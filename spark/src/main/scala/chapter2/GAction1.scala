package chapter2

import org.apache.spark.{SparkConf, SparkContext}

/**
 * action算子操作：
 * 1. 在job执行时，通过Executor的核心数和executor的个数可以确定task的并行数，如果一个stage的分区个数，
 * 无法一批次完成时，将会吧剩余的分区放到下一批次执行，然后任务运行结束。对于executor的task的并行度
 * 需要特殊计算，以保证最优的并行度。
 * <p>
 * 2. local的spark模式，是用线程模拟的任务运行，local[?],?表示的是分配给这次任务的核心数，表示executor的
 * 核心数，个数为1，即是当前主机为worker也为master也为driver。
 * <p>
 * 3.
 *
 * 所有的action算子，就是一个job：action算子之间，就是runJob().
 *
 * @author: mahao
 * @date: 2020/12/28 15:39
 */
object GAction1 {
  val conf = new SparkConf()
    .setMaster("local[3]")
    .setAppName("action算子操作")

  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {

    //创建一个测试的rdd
    val rdd = sc.parallelize(1 to 10, 3)
      .map(_ * 1)
      .map(e => (e, e))
      //.sortBy(e => e._1, false)// 这个sort操作仿佛被使用了,好像也是一个行动算子。
      .repartition(2)

    //1.reduce算子，reduce算子和reduceByKey不一样，reduce是action算子。
    //此时reduce组成了一个job，该job只有一个stage，就是读取数据到reduce的操作。
    // 任务会有3个分区，每个分区都可以并行执行任务，一直到reduce阶段，共同是一个stage。
    val tuple: (Int, Int) = rdd.reduce((e1, e2) => {
      //这个reduce的操作，会现在各个map之间进行reduce操作，在各个分区间进行数据的聚合，最后，会在driver端继续进行聚合
      //通过打印线程的id可以看出各个执行的线程。 当前的核心数个数为2，task的并行数量为2，现在为3个分区，会stage0会分成两批次去执行。
      println(s"== reduce--${Thread.currentThread().getName} == $e1,$e2  ==")
      (e1._1 + e2._1, e1._2 + e2._2)
    })
    println("reduce算子，reduce元组后的元组值是：" + tuple)

    //2.collect算子，把各个task分区中的数据集，collect到driver端。这个也是一个stage，有三个分区去运行
    //容易driver端出现oom。
    val tuples: Array[(Int, Int)] = rdd.collect()
    println(s"== collect-- 元素个数为： ${tuples.length}")

    //3.count算子，统计rdd的数据集的个数,也是在各个rdd中汇总好后，在driver最后汇种。
    val l: Long = rdd.count()
    println(s"== count-- 元素个数为： $l")

    //4.take算子，去rdd元素的前几个
    val array: Array[(Int, Int)] = rdd.take(3)
    println(s"== take-- 元素个数为： ${array.toList}")

    //5.countBykey，按照key进行统计，统计每个key的个数多数。
    //这是一个job，但是有3两个stage，一是reduce，2是collect操作，这些这个的countbykey的实现。
    val intToLong: collection.Map[Int, Long] = rdd.countByKey()
    println(intToLong)
    val tupleToLong: collection.Map[(Int, Int), Long] = rdd.countByValue()
    println(tupleToLong)

    val rdd2 = sc.parallelize(1 to 12, 2)
      .map(_ * 1)
      .map(e => (e, e))
      //.sortBy(e => e._1, false)// 这个sort操作仿佛被使用了,好像也是一个行动算子。
      .repartition(2)
    rdd2.collect();
    Thread.sleep(2000000)
  }


}
