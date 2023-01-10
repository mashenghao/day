package cn.chapter11_extends.instanceof

import cn.chapter11_extends.overrides.{ClassA, ClassB}

/**
 * 1.isInstanceOf判断一个实例，是否是一个类的实例。
 * 2. 强制转为要求的类型。
 * 3. 对于null,一定是返回false。asInstanceOf返回null。
 * ...................................................
 * // isInstanceOf只能判断出对象是否是指定类以及其子类的对象，而不能精确判断出，
 * 对象就是指定类的对象
 * // 如果要求精确地判断对象就是指定类的对象，那么就只能使用getClass和classOf了
 * // 对象.getClass可以精确获取对象的类，classOf[类]可以精确获取类，然后使用==操作符即可判断
 * ...................................................
 * 类型与match。
 *
 * @author: mahao
 * @date: 2020/12/3 9:33
 */
object Main {
  def main(args: Array[String]): Unit = {
    val classA: ClassA = new ClassB()
    val classB: ClassB = new ClassB()
    val classB2: ClassB = classA.asInstanceOf[ClassB]

    println("classA是否是ClassB的实例：" + classA.isInstanceOf[ClassB])
    println("classA是否是ClassA的实例：" + classA.isInstanceOf[ClassA])
    val aa = null
    println("对于null的判断： " + aa.isInstanceOf[ClassA])

    //2.获取实例与类的class对象。
    val clazz: Class[_ <: ClassA] = classA.getClass
    val clazz2: Class[_ <: ClassA] = classOf[ClassB]
    println(clazz)
    println(clazz2)
    println("两个对象的class是否同一个：" + clazz == clazz2)

    //3.使用模式匹配，进行类型判断,使用的是instanceof，也不是精确匹配。TODO：详细使用
    val classAB: ClassA = new ClassB()
    val classAA: ClassA = new ClassA {
      override var num: Int = _
    }
    classAA match {
      case c: ClassB => println("ClassB类型:  " + c.getClass.getTypeName)
      case c: ClassA => println("ClassA类型： " + c.getClass.getName)
      case _ => println("未知类型")
    }
  }
}
