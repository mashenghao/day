package cn.chapter6_array

import scala.collection.mutable.ArrayBuffer

/**
 * 数组案例:
 * 使用yield和函数编程剔除除第一个负数外的所有负数.
 * 两种实现：
 * 1.yield保存要留下的数据的索引，然后覆盖原数组
 * 2.Traverable的filter实现过滤。
 *
 * @author: mahao
 * @date: 2020/10/25 23:18
 */
object Array_example {

  def main(args: Array[String]): Unit = {
    val ints: Array[Int] = fun1(1, 2, 3, -1, -3, 4, 5)
    println(ints.toBuffer)

    val ints2: Array[Int] = fun2(1, 2, 3, -1, -3, 4, 5)
    println(ints2.toBuffer)
  }

  //2.第二种实现方式，使用yield记录需要保留的数据，之后在覆盖原数组。
  def fun2(as: Int*): Array[Int] = {

    //因为要覆盖写，包装成可变数组,语义上理解，要求的参数类型都是int，你传来一个序列，所以会出错的。
    //虽然可变参数的本质是序列，但是认为as是可变参数之一，但是要求的是Int，而传来的却是seq，所以类型不匹配。
    //:_*的作用，就是告诉编译器，将as作为一个整个序列参数去处理，而不是可变参数的一个参数。
    val ints: ArrayBuffer[Int] = ArrayBuffer(as:_*)
    //for循环，结合守卫，获取所有需要保留的数据索引
    var flag = true
    val keep: IndexedSeq[Int] = for (i <- 0.until(ints.length) if (ints(i) > 0) || flag) yield {
      if (ints(i) < 0) flag = false //yield每次循环通过都会经过这个。
      i
    }
    for (i <- 0.until(keep.length)) {
      ints(i) = ints(keep(i)) //覆写操作
    }
    ints.trimEnd(ints.length - keep.length)
    ints.toArray
  }

  //1.第一种实现方式，使用filter过滤实现。
  def fun1(as: Int*): Array[Int] = {
    val arrays: Array[Int] = Array(as: _*)
    var flag = true
    val ints: Array[Int] = arrays.filter((n) => {
      if (n > 0) {
        true
      } else if (flag) {
        flag = false
        true
      } else {
        false
      }
    })
    ints
  }

}
