package cn.chapter12_trait

/**
 *
 * @author: mahao
 * @date: 2020/12/3 13:50
 */
// 这种特性非常强大，其实就相当于设计模式中的责任链模式的一种具体实现依赖
trait Handler {
  def handle(data: String) {}
}

trait DataValidHandler extends Handler {
  override def handle(data: String) {
    println("check data: " + data)
    super.handle(data)
  }
}

trait SignatureValidHandler extends Handler {
  override def handle(data: String) {
    println("check signature: " + data)
    super.handle(data)
  }
}

class Person(val name: String) extends SignatureValidHandler with DataValidHandler {
  def sayHello = {
    println("Hello, " + name);
    handle(name)
  }
}

object Main3 {
  def main(args: Array[String]): Unit = {
    val p = new Person("zs")
    p.sayHello
  }

  def mm[T, U](f: () => U): U = {
    val u: U = f()
    u
  }


}

