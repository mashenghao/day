package chapter2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 1.spark创建rdd操作
 * 1.集合中创建rdd，创建ParallelCollectionRDD
 * 2.本地文件创建rdd
 * 3.hdfs文件创建rdd
 * /////////////////////
 * 1.在创建rdd的时候，有个分区个数的参数可以传入进去，建议分区为每个cpu创建2-4个分区，
 * spark会默认设置分区数的，可以查看默认的分区数计算方法，是根据集群的情况进行执行分区数的。
 *
 * 2.使用本地文件，spark在本地模式运行，只需要在本机上存在即可。如果是运行方式是在集群模式上，
 * 需要在所有的worker节点上，都要有本地文件，不然会找不到文件，因为文件不是hdfs，所以会报错的。
 * textFile可以目录或者通配符或者压缩文件读取。
 * spark对文件的分区的划分是，对每个文件的block块作为一个分区，分区不能夸文件。
 * @author: mahao
 * @date: 2020/12/24 0:57
 */
object ACreateRdd {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("rdd_create")
    val sc = new SparkContext(conf)

    //1.从集合中，创建一个初始的rdd。就是去创建一个ParallelCollectionRDD的对象，
    //其中参数    new ParallelCollectionRDD[T](this, seq.map(_._1), seq.size, indexToPrefs)
    // 分别为sc就是spark的上下文，数据集合，以前的数据集合的位置，是放一个父类的序列。
    val rddCollection: RDD[Int] = sc.makeRDD(Seq(1, 2, 3), 2) //最终放是使用parallelize去创建一个并行的rdd。

    //2. 从文件中创建rdd的类型就是Hadoop的方式，创建的rdd类型是HadoopRDD，查看spark的源码即可以看到。
    val fileRdd: RDD[String] = sc.textFile("input/chapter1/word.txt")

    rddCollection.foreach(println)
    fileRdd.collect().foreach(println(_))

    //3. 使用wholeTextFile,对一个目录中的数据进行操作，textFile是返回的每一行数据，这个是返回的一个
    //一个文件中的所有内容，lkey是文件名，和hadoop的输入CombineFileInputFormat一样、
    val wholeRdd: RDD[(String, String)] = sc.wholeTextFiles("input/chapter1/")
    wholeRdd.foreach(e=>{println(e._1 + "  "+ e._2)})

  }
}
