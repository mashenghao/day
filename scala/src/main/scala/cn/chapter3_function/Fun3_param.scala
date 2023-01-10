package cn.chapter3_function

/**
 * 函数3： 参数值
 * 1.默认参数，在参数的类型加上默认参数值，调用可以不用填写参数值。
 * 如果给出的参数不够，则会从左到右依次应用参数。
 * 2.带名参数：如果有多个默认参数，则通过指定参数名的方式赋值。
 * 3.如果含有方法重载，和方法默认值冲突，则优先调用重载的方法。
 * 4.可变参数的定义，在类型上添加*,本质原理是参数类型是序列。
 * 5.对于一个可变参数，如果序列向传入当参数，需要将序列单独遍历出来，当参数传进去。
 *  seq : _* 这种操作。(反编译看不懂实现原理)
 */
object Fun3_param {
  def main(args: Array[String]): Unit = {
    fun1("zs", 29)

    /* 最终对于默认参数的调用方式。
    int x$1 = 19;
    String x$2 = fun2$default$1();
    fun2(x$2, x$1);
     public int fun1$default$2()
     {
       return 10;
     }
     */
    fun2(age = 19)

    //fun2方法有两个，使用这个参数，可以符合fun2(name,age)参数有默认值，和fun2(name)，这个
    //则会优先匹配参数个数匹配的方法。
    fun2("zs")

    //4.定义了可变参数,使用普通参数调用。
    val zs: Int = fun3("zs", "ls", "ww")
    println("fun3结果返回值" + zs)

    //5.对于可变参数函数，参数类型不能是序列类型。因为接受和传递的参数类型都是不一致的。可以
    //将序列的值遍历出来，一个个传入进去
    fun3(Seq[String]("zs2", "ls2", "ww2"): _*)
  }

  //1.函数使用默认参数，可以在调用的时候，不指定这个参数的值。
  def fun1(name: String, age: Int = 10): Unit = {
    println(s"姓名是${name},age是${age}")
  }

  //2.函数中含有多个默认参数，如果想给单独参数赋值，通过属性名指定。
  def fun2(name: String = "zs", age: Int = 10): Unit = {
    println(s"姓名是${name},age是${age}")
  }

  //3.方法重载时，如果有默认参数，如何区分。会优先使用重载函数u.
  def fun2(name: String): Unit = {
    println(s"姓名是${name}")
  }

  //4.定义可变参数,在方法名的类型上添加*,同样可变参数必须在最后。
  //public int fun3(Seq<String> name),原理和java一样，都是数组，这个是序列。可以对参数进行序列的操作。
  def fun3(name: String*): Int = {
    for (s <- name) {
      println(s)
    }
    name.length
  }
}
