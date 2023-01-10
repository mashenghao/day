package chapter15_shuffle

import org.apache.spark.{SparkConf, SparkContext, TaskContext}

/**
 * 每个task都会创建一个TaskContext，放在了ThreadLocal中。
 *
 * @author mahao
 * @date 2021/12/21
 */
object TaskContextTest {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("测试能不能从threadLocal中取到TaskContext")

    val sc = new SparkContext(sparkConf)

    sc.makeRDD(Seq(1)).map(e=>(e,e)).join()
    sc.parallelize(Seq(1, 2, 3)).foreach(e => {
      val value: TaskContext = TaskContext.get
      println(value.stageId())
    })
  }
}
