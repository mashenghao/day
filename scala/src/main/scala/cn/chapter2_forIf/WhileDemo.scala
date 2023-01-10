package cn.chapter2_forIf

/**
 * scala的循环案例
 */
object WhileDemo {

  def main(args: Array[String]): Unit = {
    forDemo()
  }

  //for循环
  def forDemo(): Unit = {
    //1. 打印1-10，遍历序列
    for (i <- 1 to 10) { //1 to 10 为Range
      println(i)
    }

    //2.遍历数组
    for (i <- Array(1, 2, 3)) {  //为Array
      println(i)
    }
    //3.遍历序列
    for (i <- Seq(111, 11, 1)) {
      println(i)
    }
    //4.遍历序列
    for (i <- Traversable(3, 2, 1)) {
      println(i)
    }
    println("////////////////////////守卫、、、、、、、、、、、、、/")
    //5. for循环的守卫,使用守卫可以
    var i = 0
    for (i <- 1 to 10 if (i < 5)) { //守卫就是filter的操作，也是要遍历全部的。
      println(i)
    }
    //这里的i是0,for中用的i不是这个。
    println(s"循环结束后，守卫执行的结果是${i}")

    //6. 双层循环,在他们这之间还可以加守卫语句
    for (i <- 1 to 9 if (i < 8); j <- 1 to i) {
      print(s"${j}*${i}=${i * j} ")
      if (i == j)
        println()
    }

    //7. yield推导式构造集合,也是遍历后，将结果收集起来，最后赋值给res的执行过程。
    var res = for (i <- 1 to 10) yield i
    println(res)
  }

  //while循环案例
  def whileDemo() = {
    var i = 0;
    do {
      i += 1
      println(s"do-while循环: ${i}")
    } while (i < 5)
  }

  //while循环 (过程)
  def whileDemo2() {
    var i = 0
    while (i < 5) {
      i += 1
      println(i)
    }
  }

}
