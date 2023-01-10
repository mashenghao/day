package cn.chapter9_object

import scala.collection.immutable.SortedSet

/**
 * object实现枚举功能：
 * 1. java的enum就是继承一个枚举抽象类，枚举值也就是这个对象的final 静态属性。
 * 2.scala的枚举：
 * Scala没有直接提供类似于Java中的Enum这样的枚举特性，如果要实现枚举
 * ，则需要用object继承Enumeration类，并且调用Value方法来初始化枚举值
 *
 * @author: mahao
 * @date: 2020/11/7 23:04
 */
object Object_enums extends Enumeration {

  val DAY1 = Value(1, "11")
  val DAY2 = Value(2, "22")
  val DAY3 = Value(3, "33")

  def main(args: Array[String]): Unit = {
    val values: Object_enums.ValueSet = Object_enums.values
    for (elem <- values) {
      println(elem)
    }

  }
}
