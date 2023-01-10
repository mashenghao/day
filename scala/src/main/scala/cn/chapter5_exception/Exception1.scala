package cn.chapter5_exception

/**
 * scala的异常体制和java一致，
 *
 * @author: mahao
 * @date: 2020/10/25 17:59
 */
object Exception1 {

  def main(args: Array[String]): Unit = {
    try {
      throw new IllegalAccessException()
    } catch {
      case e: IndexOutOfBoundsException => {
        e.printStackTrace()
      }
      case e: MyException => {
        println("异常捕获处理")
      }
    } finally {
      println("finally 。。。")
    }
  }
}

class MyException extends RuntimeException {


}
