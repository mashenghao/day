package chapter6_stage

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * job的触发流程（wordCount案例）:
 * 第一次对job的触发与stage的划分进行理解，只是知道shufflerRdd涉及三个rdd，是承上启下的rdd，shufflerRdd的第一个map是上一个的结束，第二个map是
 * 下一个stage的开始。所以shuffle是前一个stage的最后一个rdd，但是不显示在stage中。
 *
 * @author: mahao
 * @date: 2021/1/23 11:16
 */
object AJob {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[*]")
    //.setMaster("spark://192.168.80.11:7077")
    .setAppName("AJob")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    /*
    1.只有一个foreach行动算子，所以job的个数是1。编号为job0.
    2.stage的个数是，一共有1个ShuffledRDD，所以stage的个数为2，shuffler之前的是一个stage，shuffler之后到job是一个rdd。
    stage0:是在shuffler操作之前的操作，shufflerrdd是有三个rdd构成，map，shuffler，map，map是属于stage0的，shuffler负责合并数据
    ，map负责重新作为下个rdd的读取开始。不把shuffler算子算在stage中，但是stage其实是包括shuffler的，他们是finalStage，是划分的结束。
    里面的操作被隐约了，比如map写入磁盘，shuffler合并，map又读取。
    stage1: 是整个job的finalStage的前一个算子，就是reduceRdd.map操作，但其实真正的是把foreach是在里面的，作为结束。
     */
    val fileRDD: RDD[String] = sc.textFile("input/chapter1/word.txt")
    val flatRDD: RDD[String] = fileRDD.flatMap(e => e.split(" "))
    val re: RDD[String] = flatRDD.repartition(5)
    val pairRDD: RDD[(String, Int)] = re.map(e => (e, 1))
    //reduceByKey是由3个rdd构成的 map,shuffler,map，第一个map是属于stage0，他的操作是将每个分区中的数据，
    //按照key写入到不同的文件中，shuffle与map就是进行shuffler操作了，map就是重新读取数据了，作为写一个stage的开始rdd。
    val reduceRDD: RDD[(String, Int)] = pairRDD.reduceByKey((x, i) => x + i)
    val maprdd2: RDD[(Int, String)] = reduceRDD.map((e) => (e._2, e._1))
    maprdd2.foreach(println(_))
    Thread.sleep(999999999)
  }

}
