package chapter11_sql

import org.apache.spark.rdd.RDD
import org.apache.spark.serializer.Serializer
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, GroupedData, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test
import org.apache.spark.sql.functions._


/**
 * 测试下sparksql的agg操作
 *
 * @author: mahao
 * @date: 2021/4/8 23:17
 */
class AggDemo {

  val conf: SparkConf = new SparkConf()
    .setMaster("local")
    .setAppName("AggDemo")
  val sc = new SparkContext(conf)
  val spark = new SQLContext(sc)

  case class Person(name: String, sex: String, age: Int) extends java.io.Serializable

  /**
   * 测试聚合算子sum。
   */
  @Test
  def aggSum: Unit = {

    //先去测试创建df
    val personRDD: RDD[(String, String, Int)] = sc.makeRDD(Seq(("zs", "男", 18), ("ls", "男", 19), ("ww", "女", 15)))

    val structType: StructType = StructType(Array(
      StructField("name", StringType, true),
      StructField("sex", StringType, true),
      StructField("age", IntegerType, false)))
    val personRowRDD: RDD[Row] = personRDD.map(e => {
      Row(e._1.toString, e._2.toString, e._3.toInt)
    })
    val df: DataFrame = spark.createDataFrame(personRowRDD, structType)
    println("、、、、、、、打印的数据时原数数据： show()、、、、、、、、、、、")
    df.show()

    val dataFrame: DataFrame = df.groupBy("sex")
      //第一行是group后的key，默认显示，agg里面的数据，是聚合用的。表示对同一个分组的数据，如何进行聚合
      /*
      +---+--------+---------+
      |sex|sum(age)|max(name)|
      +---+--------+---------+
      |  男|      37|       zs|
      |  女|      15|       ww|
      +---+--------+---------+

       */
      .agg(sum("age"), max("name"))
    println("//////聚合后的数据是 /////")
    dataFrame.show()

    spark.read.csv
  }
}
