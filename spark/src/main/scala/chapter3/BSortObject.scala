package chapter3

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * object对象排序:
 * 如果想要rdd元素按照自己定义的规则进行排序，则需要实现
 * spark提供的Order接口。 如果不执行，则使用的是默认的Comparable方法。
 * Order的默认就是这个接口的实现。sortby就是用来指定比较的key的。
 *
 * @author: mahao
 * @date: 2021/1/1 22:11
 */
object BSortObject {

  //如果一个rdd的对象数据集要自定义排序，则需要实现Order接口，然后定义比较规则。
  class Score(val num1: Int, val num2: Int) extends Ordered[Score] with Serializable {
    override def compare(that: Score): Int = {
      if (that.num1.compareTo(num1) == 0) {
        that.num2.compareTo(num2)
      } else {
        that.num1.compareTo(num1)
      }
    }

    override def toString: String = {
      num1 + " " + num2
    }
  }

  val conf: SparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("AwcSort")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    val fileRDD: RDD[String] = sc.textFile("input/chapter1/sort.txt", 3)
    val sortRdd: RDD[Score] = fileRDD.map(e => {
      val ss: Array[String] = e.split(" ")
      new Score(ss(0).toInt, ss(1).toInt)
    })
      //排序的规则,sortBy就会只能找sortBy指定的字段值作为比较的规则，
      //感觉应该是传入的一个Order的子类，如果不是order的子类，比如一般的值，则会被封装成为一个order的，比如
      //一般的数据类型，将会调用自身的compare进行比较。sortby实际上还是使用的sortByKey，只是自己内部封装成了
      //元组类型。按照排序的列进行排序，那一列必须是Order的类型。
      .sortBy(e => e.num2,numPartitions = 4) //这个sortby接收的函数，是用来指定按照哪一列的数据进行排序的，转为元组的时候，这个是key的函数。
    sortRdd.collect().foreach(println(_))
  }
}
