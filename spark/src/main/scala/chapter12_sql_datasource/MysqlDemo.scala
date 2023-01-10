//package chapter12_sql_datasource
//
//import org.apache.hadoop.hive.ql.exec.spark.session.SparkSession
//import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.{DataFrame, Row, SQLContext}
//import org.apache.spark.sql.functions.{max, sum}
//import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
//import org.junit.Test
//
///**
// *
// * @author: mahao
// * @date: 2021/6/15 15:16
// */
//class MysqlDemo {
//  val conf: SparkConf = new SparkConf()
//    .setMaster("local")
//    .setAppName("MysqlDemo")
//  val sc = new SparkContext(conf)
//  val spark = new SQLContext(sc)
//
//
//  @Test
//  def aggSum: Unit = {
//
//    val prop=scala.collection.mutable.Map[String,String]()
//    prop.put("user","root")
//    prop.put("password","root")
//    prop.put("driver","com.mysql.cj.jdbc.Driver")
//    prop.put("dbtable","student")
//    prop.put("url","jdbc:mysql://10.100.2.140:3306/des")
//    //从数据库中加载整个表的数据
//    val df=spark.read.format("jdbc").options(prop).load()
//    //读出来之后注册为临时表
////    df.createOrReplaceTempView("student")
////    df.show()
////    //注册好之后就可以通过sql语句查询了
////    sparkSession.sql("select * from student where age>20").show()
////    sparkSession.close()
////
////  }
//}
