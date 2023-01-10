package cn.chapter7_map

import scala.collection.immutable.SortedMap

/**
 *
 * @author: mahao
 * @date: 2020/10/27 16:08
 */
object SortMap1 {
  def main(args: Array[String]): Unit = {
    val map1 = SortedMap(1 -> "11", 2 -> "22", 3 -> "33")
    val map2: SortedMap[Int, String] = map1.+(4 -> "44")
    println(s"map1的值是 $map1 ,map2的值是 $map2 ,两个map是否相等: ${map1 == map2}")
  }
}
