package cn.chapter3_function

/**
 * 函数案例:递归实现序列累加
 */
object Fun4_example {

  def main(args: Array[String]): Unit = {
    val i: Int = sum(1, 2, 3)
    println(i)
  }

  /**
   * 定义一个函数，递归实现序列累加:
   * 第一个值，加上后面所有尾巴的值。
   * seq.tail: 截取序列，除了第一个值。
   *
   * @param seq
   * @return
   */
  def sum(seq: Int*): Int = {
    if (seq.length == 0) {
      0
    } else {
      seq.head + sum(seq.tail: _*)
    }
  }
}
