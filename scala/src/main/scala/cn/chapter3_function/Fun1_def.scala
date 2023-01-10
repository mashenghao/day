package cn.chapter3_function

import cn.chapter11_extends.Person

/**
 * 1.函数定义的方式:
 * scala的设计者任务static静态方法不符合面向对象的方式，所以在底层上，把这种替换static方法
 * 全部都生成了伴生类中的普通方法，在object中定义的方法，可以使用类名.方法去定义，但是，实际上
 * 是符合面向对象的，因为，把所有的object的中的方法，都是在伴生类中定义的，调用方式也是通过一个
 * 静态对象去实现的调用伴生类内部的方法，本质上还是对象之间的方法调用，而不是静态之间的调用。
 *2.伴生类与伴生对象二次理解:
 * Fun1_def.funcName2("s") //funcName2定义在object中
 * new Fun1_def().funcName("ss") //定义在class中
 *
 * //反编译之后
 * Fun1_def$.MODULE$.funcName2("s"); object的方法，通过伴生类的实例对象用
 * new Fun1_def().funcName("ss");  class的方法，通过new object的class实现。
 * 2.1：Object中定义的方法，编译之后，全部在伴生类中间实现，外部直接类名.方法名（funcName2d在object中）调用，
 * 则是通过Fun1_def$.MODULE$.funcName2("s");本质上，还是使用伴生类实例的方法。
 * 2.2：object才是大头，在class中的方法，都是在object中创建的，使用对象也是object new出来的，伴生类就是用来
 * 存储object中的静态方法的，其余的没什么用处了。以后使用的时候，就创建一个object，在他的class下，写语句，这些
 * 语句最终属于object。
 * 2.3: 这样做的好处是，static方法不存在了，都是普通的方法，方法的调用都是通过实例实现的。
 *
 */
class Fun1_def {

  def funcName(param1: String): Int = {
    println("class定义函数：输出函数参数值" + param1)
    Fun1_def.funcName("aa") //调用伴生对象中的静态方法，必须要通过 类名.方法，否则编译器不通过。
    Fun1_def.funcName2("aa") //调用静态方法，必须加上类型名，编译器会自动给类型加上$，然后去方法他的module实例，去调用方法。
    return 1
  }

}

object Fun1_def {

  def main(args: Array[String]): Unit = {
    //2.定义在这个位置的函数，是伴生类定义的私有方法，函数名是$1,在原有基础之上。
    def funcName(param1: String): Int = {
      println("object中main方法中：输出函数参数值2" + param1)
      return 1
    }

    val fun1_def = new Fun1_def()
    fun1_def.funcName("zs")
    funcName2("name")
  }

  //1.通用格式,定义在object的函数，就是静态方法，直接类型.方法名都可以调用了。
  def funcName2(param1: String): Int = {
    println("object定义函数：输出函数参数值" + param1)
    return 1
  }

  //2. 函数的返回值，可以通过类型推断自动推断出来，不用给出来
  def funcName3(): Int = {
    1
  }

  //class与object定义的函数是否可以同签名
  def funcName(param1: String): Int = {
    println("object定义函数与class重名：输出函数参数值" + param1)
    return 1
  }

  //class与object定义的函数是否可以同签名
  def funcName4(p:Person): Int = {
    //p = null 不支持的。p是val修饰的，引用传递。
    p.name="zs"
    p.eye_num
  }
}
