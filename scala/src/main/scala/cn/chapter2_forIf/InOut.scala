package cn.chapter2_forIf

import cn.chapter3_function.Fun1_def

import scala.io.StdIn;

/**
 * 输入输出demo
 */
object InOut {

  def main(args: Array[String]): Unit = {
    //用于读取数据
    val str: String = StdIn.readLine()
    println(s"读取到数据是${str}")
    Fun1_def.funcName2("s")
    new Fun1_def().funcName("ss")
  }
}
