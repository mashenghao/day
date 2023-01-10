package cn.chapter3_function

/**
 * 方法返回值。
 */
object Fun2_return {

  def main(args: Array[String]): Unit = {

  }

  //1. 单行函数,可以不加大括号
  def fun1() = println()

  //2.当不添加函数返回值时，会自动进行类型推导，如果不加=，则无返回值
  def fun2() = {
    ""
  }

  //对于函数的返回值为Unit的时候，即不需要函数返回值的时候，称为过程：
  def fun3(): Unit = {
    println("过程函数定义1： 返回值为Unit")
  }

  def fun4() = {
    println("过程函数定义2： 不写返回值，也不返回任何数值")
  }

  def fun5() {
    println("过程函数定义3： 不写=号，则默认的返回值就是Unit")
  }

  def fun6 {
    println("过程函数定义4： 不写=与（）号，则默认的返回值就是Unit")
  }
}
