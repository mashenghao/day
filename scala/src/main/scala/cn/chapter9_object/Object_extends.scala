package cn.chapter9_object

//定义一个抽象类
abstract class Abstract1 {
  def work(): Unit
}

/**
 * 1.object也可以继承抽象类，实际的继承的是object$继承的，这也是问什么bootStrap要
 * 反射获取Object$的实例一样。伴生对象的抽象方法也会在伴生类中生成静态方法签名。
 *
 * @author: mahao
 * @date: 2020/11/7 22:18
 */
object Object_extends extends Abstract1 {
  override def work(): Unit = {
    println("object_extends")
  }

  def main(args: Array[String]): Unit = {
    work()
  }
}
