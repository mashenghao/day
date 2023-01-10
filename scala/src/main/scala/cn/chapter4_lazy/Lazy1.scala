package cn.chapter4_lazy

/**
 * lazy属性值的特性：
 * 1. 当被lazy修饰的变量，只有在第一次使用的时候，才会被赋值。
 * 目前理解就是将计算封装成一个函数，等到真正调用的时候，才去了调用
 *
 * @author: mahao
 * @date: 2020/10/25
 */
object Lazy1 {

  def main(args: Array[String]): Unit = {
    //1.num被声明为lazy，只有第一次使用时方法才会被执行。
    lazy val num = fun1()
    println("main 执行")
    println("num数值是： " + num)

    //2.对于文件IO，只有使用时才会报错。
    import scala.io.Source._
    val line: String = fromFile("d:\\aa.txt").mkString
    println(line)
  }

  def fun1(): Int = {
    println("fun1函数被调用")
    1
  }

}
