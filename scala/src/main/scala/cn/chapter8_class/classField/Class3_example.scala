package cn.chapter8_class.classField

/**
 *
 * @author: mahao
 * @date: 2020/12/2 23:53
 */
/**
 * 这个构造函数，里面的参数，会被定义为成员变量，修饰符会被生效。
 *
 * @param name private的属性，只允许被类内部访问，伴生对象中也不行访问。
 * @param age  默认为public，是var类型。
 * @param sex  private[this] 只允许在内部使用。
 */
class Class3_example(private val name: String, var age: Int, private[this] var sex: String) {

  def compare(obj: Class3_example): Boolean = {
    //2.在类的内部，可以访问到所有的变量, 但是不可以访问到 private[this]这个变量。
    println(obj.name)
    println(s"访问对象的 private[this] 的属性是无法访问到其他对象的属性的:   obj.sex" )//这个无法访问。
    return false
  }
}

object Class3_example {
  def main(args: Array[String]): Unit = {
    //1.外部类调用的只可以是public属性，对于伴生对象中，是可以访问私有变量的。
    val obj: Class3_example = new Class3_example("zs", 18, "男")
    val age: Int = obj.age
    println(s"在伴生对象中，访问私有变量值是： ${obj.name}")
  }
}