package cn.chapter8_class.classConstructor

/**
 * scala的构造函数
 *
 * @author: mahao
 * @date: 2020/11/4 0:13
 */
/*
1.主构造函数在class签名处声明。主函数的方法体是class中函数和属性之外的代码
2.主构造中的传参，参数没有修饰符，如果在函数内没有使用，只在主构造函数中，
就不会生成一个属性。如果使用了其他函数使用了，就会定义为private[this]这种修饰。
3.被val 或者 var 修饰则会创建属性。
 */
class ClassA(val age: Int, sex: String = "男") {

  //在class中，除方法和属性外的代码，都是主构造函数的代码体
  println("主构造函数被调用、、、" + sex)


  var name: String = _

  /*
  1、scala的构造函数支持重载：但是第一行必须是显示调用主构造函数：
   */
  def this(name: String) { //还是会优先匹配函数的，匹配不上才会使用参数默认值的。
    this(1, "女") //必须在此处显示调用主函数
    this.name = name
    println("辅助函数被调用")
  }

}

object ClassAObject {
  def main(args: Array[String]): Unit = {
    val classA = new ClassA("ls")

  }
}