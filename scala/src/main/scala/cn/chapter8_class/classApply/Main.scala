package cn.chapter8_class.classApply

import cn.chapter8_class.classApply

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
 *
 * 总结：
 * object会创建一个新的类，叫做伴生类,Class$,这个Class$中，有object中定义的所有的方法，他作为Class最终类
 * 的一个辅助存在，用来将Class中的静态方法转为Class$.MODULE$.方法名。
 * 2. 也可以在里面定义apply方法，即Class$中就有了这个方法，可以用它来创建对象，在Class中也可以定义apply，
 * 用来操作具体的实例，这两个不冲突。
 * 3.只要以后记得，遇见object，就是创建了一个Class$类，什么逻辑都是在Class$中，外部通过Class类名访问的都是
 * 在自己的Class$中实现。同时集合MODULE$对象。
 * }
 */
object Main {
  def main(args: Array[String]): Unit = {
    /**
     * 2.自定义了一个case 类，object apply方法创建了类实例。
     * 在object中创建了apply方法，用于创建class对象，在class中创建了apply方法，用于
     * 操作实例。
     * 如果只在object中定义了apply，实例访问不到，因为生成的位置，在object伴生类中，object可以
     * 通过获取伴生对象的静态实例，去调用这个伴生类中apply方法。
     * class是没有这个函数的，也没有调用实例，class就是编译后class不到$的。
     *
     */
    val classA = new classApply.ClassA
    val classa1 = classApply.ClassA()
    classA("zs") //这个是调用的apply的方法
    classA.apply("zs");
    classA.update("zs", 19);
    classA("zs")=20 //这个调用的是update方法。

  }

}
