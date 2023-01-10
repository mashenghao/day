package cn.chapter6_array

/**
 * scala数组案例： 数组的元素是可变的，但是长度不允许改变。
 * 1.数组定义：通过new Array[泛型](长度)，或者使用Array()的apply方法，泛型可以自动类型推断。
 * scala的数组类型，是一个Array类，底层仍然是jvm的数组类型[String这种，就是对其封装了一次，
 * 使其有操作方法。
 *
 * @author: mahao
 * @date: 2020/10/25 18:10
 */
object Array1 {

  def main(args: Array[String]): Unit = {
    //1.数组的创建，Array传入泛型，和数组长度，数组是不可变的。
    val arr1: Array[String] = new Array[String](3)

    //2.scala中的数组是类型，不同数组是泛型定义的，可以直接使用apply方法创建。
    /*
    def apply[T: ClassTag](xs: T*): Array[T] = {
    val array = new Array[T](xs.length)
    var i = 0
    for (x <- xs.iterator) { array(i) = x; i += 1 }
    array
  }
     */
    val arr2: Array[Any] = Array(1, 2, 3, "aa")

    //3. 数组增删改查
    println(s"获取数组的长度与第二个元素：${arr2.length} , ${arr2(1)}") //调用的是对象的apply方法
    println(s"修改索引为1的数组值为18，${arr2(1) = 18}, 修改后的结果是${arr2(1)}") //调用的是update方法
    arr2.update(3, 10) //修改操纵,数组操作不可越界。

    print(arr2) //arr2类型本来是数组，但是方法入参是seq，会发生隐式转换。

    val ints = List(1, 2)
    val ints2 = ints ::: List(3, 4)
    println(ints2)
    println(1 :: 2 :: 3 :: Nil.::(4))
    var jetSet = Set ("Boeing","Airbus")
    jetSet += "Lear"
  }

  //定义一个泛型方法，在方法名后声明
  def print[T](arr: Seq[T]): Unit = {
    println("数据类型是： " + arr.getClass.getTypeName)
    arr.foreach(println)
  }
}
