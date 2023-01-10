package cn.chapter7_map

/**
 * 不可变map：
 *
 * @author: mahao
 * @date: 2020/10/27 14:09
 */
object Map1 {

  def main(args: Array[String]): Unit = {
    //1、通过Map的apply方法直接创建，使用的是不可变map,是immutable.Map$Map2下的实现类
    val map1: Map[Int, String] = Map(1 -> "zs", 2 -> "ls", (3, "ww"))
    println(s"s输出的map值是：$map1 类型是：${map1.getClass}")

    //2.map的取值，通过括号，如果map中的元素，不存在则会抛出异常
    val str: String = map1(1)
    //通过get方法获取到Option，在通过match判断。
    val maybeString: Option[String] = map1.get(1)
    val str1: String = maybeString match {
      case None => throw new RuntimeException("没有元素值")
      case Some(value) => value
    }
    println(s"获取map中的值，通过小括号key，$str")
    val str3 = if (map1.contains(2)) map1(2) else 0
    val str33: String = map1.getOrElse(34, "zs") //等价于上面的操作。
    println("获取到的数据是Str33:  " + str33)

    //3. 赋值操作，不允许更改数据
    // map1(1) = "zs"

    //4.增减元素，对于不可变map而言，是不可以增加，但是可以新创建一个。
    //新创建的map仍然是不可变的map。
    val map2: Map[Int, String] = map1.+(4 -> "ml", 5 -> "55")
    println(s"新的map2是：$map2 , 老的是 $map1")
    val map3: Map[Int, String] = map2.++(map1) //可以用++ 添加一个map。
    println(map3)

    //5.遍历map
    println("使用for循环，元素类型是元组")
    for ((k, v) <- map2) {
      println(s"元素的key是$k , 值是 $v")
    }
    println("使用foreach函数，遍历")
    map2.foreach((e) => {
      println(s"(${e._1}->${e._2})")
    })

    println("直接遍历values")
    val values: Iterable[String] = map1.values
    val iterator: Iterator[String] = values.iterator
    while (iterator.hasNext) {
      println(iterator.next())
    }

  }
}
