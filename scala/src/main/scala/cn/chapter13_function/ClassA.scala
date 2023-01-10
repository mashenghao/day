package cn.chapter13_function

import org.junit.Test

/**
 * 函数测试
 *
 * @author: mahao
 * @date: 2020/12/3 14:23
 */
class ClassA {

  //闭包
  @Test
  def demo1(): Unit = {
    //定义一个函数，返回类型是函数
    def f1(num: Int): (Int) => Int = {
      val returnfu = (x: Int) => x + num //定义返回的函数
      returnfu
    }

    val f1_99 = f1(99)
    val i: Int = f1_99(1)
    println("99参数的返回值是" + i)

    val f1_1 = f1(1)
    val j: Int = f1_1(1)
    println("1参数的返回值是" + j)
  }

  //克里化函数
  def fun1(i: Int): Int => Int = {
    val f = (x: Int) => i + x
    f
  }

  def fun2(i: Int)(y: Int): Int = {
    i + y
  }
}
