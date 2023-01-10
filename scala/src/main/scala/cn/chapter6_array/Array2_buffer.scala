package cn.chapter6_array

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * 数组操作2：可变数组.
 *  ArrayBuffer类似ArrayList,内部是数组，可以自己扩容。
 * 1. 创建，通过apply方法，或者new
 * 2. array和ArrayBuffer之间的互相转换。
 * 3. array的遍历(seq的遍历)
 *
 * @author: mahao
 * @date: 2020/10/25 18:30
 */
object Array2_buffer {

  def main(args: Array[String]): Unit = {
    //1.scala使用ArrayBuffer可以是可变的数组。
    var arr1 = new ArrayBuffer[String]()
    arr1.append("00", "11") //添加一个或者多个元素
    arr1.+=("22") //添加一个元素
    arr1.++=(Seq("33", "44")) //添加一个序列，可以添加其他数组。
    println(arr1.toString())

    //2.删除元素
    arr1.remove(2) //删除指定索引的元素
    println(arr1.toString())
    arr1.trimEnd(2) //截取掉后面的元素。
    println(arr1)

    //3.使用insert插入元素,指定索引位置插入
    arr1.insertAll(2, Array("22", "33"))
    println(arr1)

    //4. 在Array与ArrayBuffer之间互换
    val array: Array[String] = arr1.toArray
    val buffer: mutable.Buffer[String] = array.toBuffer
    println("原buffer： "+arr1.hashCode())
    println("Array: "+array.hashCode())
    println("array转回buffer： " + buffer == arr1)


    //vector是不可变集合，元素值也不可以改变
    val v = Vector[Int](1, 2, 3)
    println(v)
    val i: Int = v(1)
    println(i)

    //5.数组遍历
    //通过for循环，通过索引遍历
    for (i <- 0.until(arr1.length, 1)) {
      print(arr1(i) + " ")
    }
    println()
    //逆序遍历，间隔为2
    for (i <- (0.until(arr1.length)).reverse) {
      print(arr1(i) + " ")
    }
    println("///////")

    val str: String = arr1.mkString(",")
    println(str)

    //5.apply 和 update 函数的操作。
    val str1: String = arr1(1)
    arr1(1)="33"
  }
}
