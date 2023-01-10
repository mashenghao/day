package cn.chapter6_array

import scala.collection.mutable.ArrayBuffer

/**
 * 数组转换:将一个数组转换成另一个数组。
 * 1. yield，可以通过for循环和yield组合，构造新的数组。转换后的数据类型还是数组。
 * 2. 通过scala的函数式编程。Traversable接口下的函数式方法。
 *
 * @author: mahao
 * @date: 2020/10/25 22:06
 */
object Array_transver {

  def main(args: Array[String]): Unit = {
    //1.使用yield构造数组Array，查看后返回的结果仍然是数组类型。
    val arr1 = Array(1, 2, 3, 4)
    //使用for循环添加守卫，可以过滤，yield也可以对结果进行操作。
    val arr11: Array[Int] = for (a <- arr1 if a < 3) yield a * a
    println(s"使用yield转换Array数据类型是 ${arr11.getClass},结果值是 ${arr11.toBuffer}")

    //2.使用yield构造BufferArray，查看结果仍然是ArrayBuffer。
    val arr2 = ArrayBuffer("a", "b", "c")
    val arr22: ArrayBuffer[Any] = for (elem <- arr2) yield {
      if (!elem.equals("a")) elem + " aa "
    }
    println(s"使用yield转换的ArrayBuffer数据类型是 ${arr22.getClass},结果值是 $arr22")

    //3.使用函数式编程，转换数组,filter和map方法都是集合的父接口
    val arr3: Array[Int] = Array(1, 2, 3, 4)
    val arr33: Array[Int] = arr3.filter(_ % 2 == 0)
    val arr333: Array[Int] = arr33.map(_ * 2)
    println(arr3.hashCode())
    println(arr33.hashCode())
    println(arr333.hashCode())


  }
}
