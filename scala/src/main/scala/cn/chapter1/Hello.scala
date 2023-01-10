package cn.chapter1

/**
 * hello
 */
class AHello {
  def m1(): Unit = {
    print("m11")
  }

  def m2(): Unit = {
    print("m12")
  }
}

object AHello {
  def main(args: Array[String]): Unit = {
    print("hello")
  }

  def m1(): Unit = {
    print("m1")
  }

  def apply(): AHello = new AHello()
}