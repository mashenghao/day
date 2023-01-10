package cn.chapter11_extends.init

import cn.chapter11_extends.Person

/**
 * scala的继承是extends关键字，可以继承父类的非私有属性和方法。
 * 但是如果父类用final修饰，field和method用final修饰，
 * 则该类是无法被继承的，field和method是无法被覆盖的
 *
 * @author: mahao
 * @date: 2020/11/7 23:54
 */
class Stu1 extends Person {

  var score: Int = 100
}

object Stu1 {
  def main(args: Array[String]): Unit = {
    val s1 = Stu1()
    val name: String = s1.name
    println(s"访问父类的属性值是$name")
    s1.m1
  }

  def apply(): Stu1 = new Stu1()
}
