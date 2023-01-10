package cn.chapter12_trait

/**
 *1. 将trait作为接口来使用。
 *2. trait中也可以定义具体的方法。
 *3. 在Trait中定义具体字段。和继承不同的是，这个实际是在子类中，继承的话，字段在父类中。
 *4.
 *
 * @author: mahao
 * @date: 2020/12/3 13:04
 */
//1. trait中可以定义抽象属性与抽象方法
//2. trait中也可以定义普通的方法。
trait Trait1 {

  //4. 定义抽象字段
  var name: String

  // 3. Scala中的Triat可以定义具体field，此时继承trait的类就自动获得了trait中定义的field
  // 但是这种获取field的方式与继承class是不同的：如果是继承class获取的field，实际是定义在父类中的；而继承trait获取的field，就直接被添加到了类中
  val eye_num = 2

  def fun1(): Unit

  def fun2(): Unit = {
    println("trait1 中定义的普通方法。")
  }
}

trait Trait2 {
  def m1(): Int
}

//trait的实现类必须实现所有的方法。
class Demo1 extends Trait1 with Trait2 {

  override var name: String = _

  override def fun1(): Unit = ???

  override def m1(): Int = ???
}
