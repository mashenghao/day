package cn.chapter11_extends.constract

/**
 *
 * @author: mahao
 * @date: 2020/12/3 10:11
 */
class Parent(var name: String, val age: Int) {

}

/**
 * 必须在子类主构造函数中，显示调用父类的构造函数。父类的参数是子类的子集。
 *
 * @param name
 * @param age
 */
class Son(name: String, age: Int, sex: String) extends Parent(name, age + 1) {

  //构造函数重载。
  def this(sex: String) {
    this("zs", 18, sex)

  }
}

//对于object而言，直接赋值就可以了。
object Son extends Parent("zs", 19) {
}