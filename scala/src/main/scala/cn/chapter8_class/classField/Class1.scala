package cn.chapter8_class.classField

/**
 *1.class的属性：
 * 1.属性的访问修饰符：
 * 默认public：
 * private：
 * private[this]:
 * 2.反编译生成的默认get和set方法实现的格式
 * 3.private[this] 的如何实现的。
 * 4. s.name 实际代码是如何，通过s.name()调用的。
 *
 * @author: mahao
 * @date: 2020/11/3 22:50
 */
class Class1 {
  //1.属性可以定义为private的，如果定义为
  //private，则默认的创建的name(),name_()方法，也是私有的。
  /*
   private String name = "zs";

  private String name() {
    return this.name;
  }

  private void name_$eq(String x$1) {
    this.name = x$1;
  }
   */
  private var name = "zs"


  /*
  2.不加修饰符，则默认的是public修饰的，外部可以访问到对象，访问用对象引用.属性名即可，
  其实是使用的属性的get方法。但是对于val变量，是被final修饰的，没有生成set方法，只有get方法。
  public int age() {
    return this.age;
  }
   */
  val age = 10

  /*
  定义为private[this]，则只能在当前的对象内使用属性，在该类下的其他对象中，是无法使用到这个
  属性的。这个的实现，是通过不生成get 和 set实现的，因为你要想访问这个变量，必须通过get方法，不通过
  则是无法访问的。
   */
  private[this] var sex: String = _

  //自定义一个getName的函数，提供给外部使用。
  def getName = name

  def apply() = {
    println("我是class内部的apply方法，会在 对象() 时被调用 ！！！")
  }

  //校验private 和 private[this]的区别。
  def compare(o: Class1): Unit = {
    if (o.name.equals(name)) {
      println("两个对象的name属性相同")
    }
    println(sex)
    //下面的操作，将会报错，因为o的age不能在对象外被访问。
    //if (o.sex.equals(sex)) println()
  }
}

/*
外部类的Main对象，用来启动测试Class1
 */
object Main {
  def main(args: Array[String]): Unit = {
    /*
    1.测试class的apply方法，创建个对象，然后使用对象().反编译后的代码是：
    Class1 class1 = new Class1();
    class1.apply();
     */
    val class1 = new Class1
    class1() //这里就会调用apply方法。

    /*
    2.name的取值方法获取不到，通过自定义的函数去获取：
     */
    // class1.name 获取不到，如果是伴生对象，则可以，因为会给这个属性，创建一个
    // public方法，然后调用这个方法获取属性。
    val name: String = class1.getName
    println(s"使用getName方法获取到的数据是: $name")

    /*
    3.获取age的属性，其实是调用的
     */
  }
}
