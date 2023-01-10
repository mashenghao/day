package cn.chapter12_trait

/**
 * 5.给实例混入trait
 *
 * @author: mahao
 * @date: 2020/12/3 13:37
 */
trait Trait11 {
  //定义一个trait中的方法。
  def log(msg: String): Unit = {
    println(s"msg = $msg")
  }

  def m1
}

class ClassA {

}

object Main {

  def main(args: Array[String]): Unit = {
    //5. 创建一个ClassA实例，这个实例创建时，继承Trait1
    val classa = new ClassA with Trait11 {
      override def m1: Unit = println("m1方法")
    }
    //不能指定类型为ClassA，因为不存在trait1的方法。这个类型是scala自己生成的
    val clazz: Class[_ <: ClassA ] = classa.getClass
    println("混入实例的类型是： " + clazz.getTypeName)


  }
}
