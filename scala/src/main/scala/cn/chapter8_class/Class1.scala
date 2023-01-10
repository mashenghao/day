package cn.chapter8_class

import scala.collection.mutable

/**
 * scala中的一些默认：
 * 1、如果方法中只有参数，就可以不用参数和.来调用。比如0.to(2) 就是 0 to 2
 * 2、scala中的基本数据类型都是对象，因此0可以有方法，其实是RichInt的方法，实际
 * 上 1+1 就是1.+（1）的操作。
 * 3、当在某个值后面使用()这种时，如数组变量arr(1),map("k1"),其就是调用的对应的对象的apply方法。
 *
 * @author: mahao
 * @date: 2020/10/27 19:36
 */
object Class1 {

  def main(args: Array[String]): Unit = {
    fun1()

    /**
     *1.对于直接在变量后使用(),就是调用对象的apply方法。无论是在类后面或者
     * 变量后面，都是调用的apply方法。
     * 1.1 类后面调用apply方法，调用的apply方法，返回值是用来返回具体的类实例的，
     * 比如Map[String, Int]("a" -> 1)，apply方法是返回的一个map实例，这个是GenMapFactory的apply方法
     * def apply[A, B](elems: (A, B)*): CC[A, B] = (newBuilder[A, B] ++= elems).result()，用来
     * 创建一个map实例。
     *
     * 1.2 变量调用apply的方法，调用的是对象的apply方法，函数作用是操作对象的，
     * map("k1") 方法是，是MapLike具体子类的方法，用来操作对象使用的。
     * def apply(key: K): V = get(key) match {
     * case None => default(key)
     * case Some(value) => value
     * }
     */
    val map = Map[String, Int]("a" -> 1)
    println(map.getClass)
//    map("k1")

    //这个apply函数和上面的一致，也是GenMapFactory的apply方法，对于map2的
    //apply,则调用的类型是HashMap去调用了。
    val map2 = mutable.HashMap[String, Int]("a" -> 1)
    map2("a")



  }

  def fun1(): Unit = {
    val array = Array(1, 2, 3, 4)
    /*
    这个f1函数，在编译期间，会被创建成一个内部类，这个类继承
    AbstractFunction1<Object, String>，定义的函数就是apply函数的
    内容。
    Class1$$anonfun$1 class1$$anonfun$1 = new Class1$$anonfun$1();
     */
    val f1 = (e: Int) => {
      println("函数被调用，参数是: " + e)
      e + ""
    }
    val strings: Array[String] = array.map(f1)
    println(strings)

  }
}
