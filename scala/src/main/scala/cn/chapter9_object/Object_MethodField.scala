package cn.chapter9_object

/**
 *1. object，
 * 1.object中的方法和属性，都是静态的(静态实例，去调用方法。)，伴生类访问
 * 这些方法和属性。因为object的方法和属性，都在Object$类中，所以他们之间的定义
 * 和访问规则就和java中一致。（注意，main函数还是在伴生类中，而且object中函数，在class中都有
 * 一个静态方法签名，去调用object$类。）。
 * 2.class中可以访问到object中的私有变量和私有方法。
 * private val name: String = "object field"
 * private def f1 = println("object method")
 * //object生成的字节码，会将私有属性名和方法名进行重命名，如果在伴生类或者object中内部
 * //使用scala的编译器，会把之前的f1，替换成这个新的public方法，所以可以使用了。
 * public String cn$chapter9_object$Object_MethodField$$name() {//对应object中name属性的get方法，命名方法是类名，然后属性名
 * return this.cn$chapter9_object$Object_MethodField$$name;
 * }
 *
 * public void cn$chapter9_object$Object_MethodField$$f1() {//对应f1方法，重新生成了一个public的方法。
 *     scala.Predef$.MODULE$.println("object method");
 * }
 * 所以，class中可以调用object中的私有的静态方法和静态属性都是通过编译器，去重新创建一个新的public的方法或者属性，在class中
 * 将原来的在翻译过来。这就是原理，如果是这样的话，在其他类中，要是使用object中的私有变量或者方法，是不是可以通过使用public后的
 * 那个方法。？？？ 编译器校验不通过。
 *
 *3. object中也可以访问到class中的私有变量和私有方法。
 * 通过上面的联想，很容易想到，在class中私有方法或者属性，在object中被访问，也是将他们转为public类型的其他方法名，然后object中这样调用。
 *
 * @author: mahao
 * @date: 2020/11/7 21:40
 */
class Object_MethodField {


  //在伴生类中，可以访问伴生对象中的私有方法。
  def m1 = {
    val name: String = Object_MethodField.name
    println(s"获取伴生类中的变量为: $name")

    Object_MethodField.f1
  }

  private def m2 = {
    println("class m2")
  }
}

object Object_MethodField {

  private val name: String = "object field"

  private def f1 = println("object method")

  def f2 = println("object method 2 ")


  def main(args: Array[String]): Unit = {
    f1
    println(name)

    //在object中，同样可以访问到class的私有变量和私有方法。
    val classa = new Object_MethodField
    classa.m2
  }
}
