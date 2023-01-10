package chapter2

import org.apache.spark.{SparkConf, SparkContext}

/**
 * spark的rdd中pairRdd对元组数据类型进行单独的操作。如果java测试的话，
 * 需要手动调用 <tt>XXXtoPair</tt> 算子。参看{@link part1.Hello2}
 * 案例演示使用隐式转换为PairRdd去操作数据。
 * 演示的原因早BRdd中也有注释，因为在Rdd的方法声明中，没有reduceByKey等这些对元组的数据
 * 进行操作的函数，JavaRdd也没有针对这些的操作。 scala对这种的操作是通过使用隐式转换，自动转换为
 * pariRddFunction，java中是有代码手动创建了，比如mapToPairRdd，这时候就要求返回值是元组了。
 * <p>
 * 案例： 统计相同行出现的次数。
 *
 * @author: mahao
 * @date: 2020/12/26 23:26
 */
object CPairRdd {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("PairRdd")

    val sc = new SparkContext(conf)

    val tuples: Array[(String, Int)] =
      sc.textFile("input/chapter1/word.txt")
        .map((_, 1)) //就在这里发生了隐式转换。
        .reduceByKey(_ + _)
        .collect()

    tuples.foreach(e => println(e._1 + "  -->  " + e._2))
  }
}
