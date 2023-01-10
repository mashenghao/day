package cn.chapter7_map

import scala.collection.mutable
import scala.collection.mutable.Map

/**
 * map使用：创建可变的map
 *
 * @author: mahao
 * @date: 2020/10/27 15:12
 */
object Map2 {
  def main(args: Array[String]): Unit = {
    //1.创建一个可变map,scala.collection.mutable.HashMap默认的数据类型
    val map1 = new mutable.HashMap[Int, String]()
    val map11 = Map(11 -> "zs11") //可变集合
    println(map1.getClass + " __ " + map11.getClass)

    map1.+((1, "2"))
    //2.添加元素，删除元素
    map1.+=(1 -> "zs", 2 -> "ls") //数据添加到map中
    map1.++=(map11) //用++= 可以添加一个map。
    println("添加后的元素map1是： " + map1)
    //删除key的元素
    map1.-=(22)
    map1(1) = "22"
    //使用get或者put或者remove获取值。将会Option,没有值，不会报错。
    val op1: Option[String] = map1.get(11)
    println(op1)
    val op2: Option[String] = map1.put(1, "zs")
    println(op2)

    //3. 修改元素
    println(s"修改前元素的值是： $map1")
    map1(1) = "zs1"
    map1(111) = "zs111" //修改不能存在的元素值,
    println(map1)

    //4.遍历
    // ...
    println("遍历")
    //foreach代码是 map.foreach( f:((K,V))=>U):Unit=forEntry(e=>f(e.key,e.value))
    map1.foreach((e) => {
      println(e._1 + e._2)
    })
  }
}
