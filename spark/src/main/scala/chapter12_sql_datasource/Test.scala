package chapter12_sql_datasource

import org.apache.spark.storage.StorageLevel

/**
 * @author: mahao
 * @date: 2021/11/04
 */
object Test {
  def main(args: Array[String]): Unit = {
    val ss = Seq(Seq("aaa", "vvv"), Seq("vv", "aaa"), Seq("aaa", "ss"))
    val value = ss.fold("a")((x, y) => x + y.toString)
    println(value)
  }
}
