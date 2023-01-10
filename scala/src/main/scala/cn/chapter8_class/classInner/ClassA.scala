package cn.chapter8_class.classInner

import scala.collection.mutable.ArrayBuffer

/**
 *
 * @author: mahao
 * @date: 2020/11/4 12:47
 */
class ClassA {

  class Student {}

  def apply(): ArrayBuffer[Student] = {
    val list = ArrayBuffer(new Student)
    list += new Student
    list.append(new Student)
    list
  }
}

object MainS {
  def main(args: Array[String]): Unit = {
    val classa = new ClassA
    val students: ArrayBuffer[classa.Student] = classa()
    students.+=(new classa.Student)

    val classb = new ClassA
    //students.+=(new classb.Student) ,不允许使用，不是同一个外部类对象。
  }
}
