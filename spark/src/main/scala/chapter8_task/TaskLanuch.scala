package chapter8_task

import org.apache.spark.deploy.yarn.Client
import org.apache.spark.{SparkConf, SparkContext}

/**
 * task的启动原理： TaskRunner线程类，封装task的方法逻辑，在每个
 * executor中有线程池，运行taskRunner。
 *1. executor接收到LanuchTask请求，TaskRunner运行。
 * 2. TaskRunner做一些准备操作，反序列化task，拉取文件与jar包，之后，运行run方法。
 * 3. taskRunner内部会调用task的run方法，这个run方法就是去真正实现的计算逻辑，这个run方法内部的核心是调用rdd的iterator方法，在这里，就会针对不同的分区，执行自定义的算子操作。
 * 4.1 ShuffleMapTask，计算完partition中的数据后，实际上会使用shufflerManager的shuffleWriter，将数据写入对应的分区文件。
 * 2 所有操作完成后，实际上会有一个mapStatus，发送给DagScheduler,将mapStatus发挥给MapOutputTracker，这个记录着shufflerWriter的数据写出位置。
 * 4.2 resultTask是针对ShuffletMapTask的输出（RDD），来执行shuffle操作，可能会去MapOutPutTracker中输出数据。
 *..\bin\spark-submit --class chapter8_task.TaskLanuch --master spark://192.168.80.11:7077 ..\jars2\spark-1.0-SNAPSHOT.jar
 * @author: mahao
 * @date: 2021/2/21 22:55
 */
object TaskLanuch {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      //.setMaster("spark://")
      .setAppName("TaskLanuch")
    val sc = new SparkContext(conf)
    sc.makeRDD(1 to 10, 2)
      .repartition(4)
      .foreach(println(_))
    val i1: Int = sc.makeRDD(1 to 10, 2)
      .aggregate(0)((a, i) => { //会触发job。
        println(Thread.currentThread().getName + "-- " + a + "---" + i)
        a + i
      }, (a, b) => a + b)
    println(i1)


  }
}
