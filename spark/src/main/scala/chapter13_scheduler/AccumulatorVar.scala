package chapter13_scheduler

import org.apache.spark.{Accumulable, AccumulableParam, Accumulator, AccumulatorParam, SparkConf, SparkContext}

import java.util
import scala.collection.mutable.ListBuffer

/**
 * 测试累加变量是否可以在两个stage中用。
 *
 * @author mahao
 * @date 2021/12/27
 */
object AccumulatorVar {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("测试累加变量是否可以在两个stage中用。")

    val sc = new SparkContext(sparkConf)

    val acc: Accumulator[Long] = sc.accumulator(0L, "acc")

    val accList: Accumulable[ListBuffer[String], String] =
      sc.accumulable(new ListBuffer[String], "accList")(new AccumulableParam[ListBuffer[String], String] {
        override def addAccumulator(r: ListBuffer[String], t: String): ListBuffer[String] = {
          println("//////  调用 mergeValue ///////")
          r.+=(t)
        }

        override def addInPlace(r1: ListBuffer[String], r2: ListBuffer[String]): ListBuffer[String] = {
          println("//////  调用  合并集合  ///////")
          r1.++=(r2)
        }

        override def zero(initialValue: ListBuffer[String]): ListBuffer[String] = {
          println("//////  调用 create ///////" + initialValue)
          val strings = new ListBuffer[String]
          strings += 0.toString
          strings
        }
      })

    sc.parallelize(Seq(1, 2, 3, 4, 5, 6), 2)
      .map(e => {
        acc.add(1)
        accList.add(e.toString)
      })
      .count()

    println(acc.value)
    println("//////////  " + accList.value)

    sc.parallelize(Seq(1, 2, 3, 4, 5, 6), 2)
      .map(e => {
        acc.add(1)
      })
      .count()

    println(acc.value)
  }

}
