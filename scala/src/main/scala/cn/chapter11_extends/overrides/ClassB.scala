package cn.chapter11_extends.overrides

/*
public abstract class ClassA {
  public abstract int num();
  public abstract void num_$eq(int paramInt); //其实子类就是对他的实现。

  private final int num2 = 10;
  public int num2() { return this.num2; }

  private int num3 = 10;
  public int num3() { return this.num3; }
  public void num3_$eq(int x$1) {  this.num3 = x$1; }

  public void work() {
    Predef$.MODULE$.println("classA work");
  }
}
 */
/**
 * 1.abstract抽象类中可以定义没有初始化的属性。var 和 val修饰都可以，因为会定义一个抽象
 * 的get和set方法。子类对属性的实现，就是对他们的实现。
 */
abstract class ClassA {
  //定义一个抽象对象，类型是var.
  var num: Int //可以覆写，因为他是定义为一个get的抽象方法。
  val num2: Int = 10 //val修饰的可以覆写，因为他是子类覆写的get方法，通过super.num2也能返回回来
  var num3 = 10 //他是不可以被覆写的。因为他是变量名，可以访问

  def work(): Unit = {
    println("classA work")
  }
}

/*
public class ClassB extends ClassA {
  private int num;

  public static void main(String[] paramArrayOfString) {
    ClassB$.MODULE$.main(paramArrayOfString);
  }

  public int num() {
    return this.num;
  }

  public void num_$eq(int x$1) {
    this.num = x$1;
  }

  private final int num2 = 100;

  public int num2() {
    return this.num2;
  }

  public void work() {
    Predef$.MODULE$.println("classB work");
    super.work();
  }
}
 */
/**
 * 1.实现或者重写父类的方法，必须添加override关键字，在子类中
 * 调用父类的方法通过super关键字。
 *2. 属性也可以被重写，子类可以覆盖父类的val field。也可以覆写没有初始化的var变量。
 *
 * 总之就是可以覆写父类的抽象属性和方法，对于val变量，也可以覆写。
 *
 * @author: mahao
 * @date: 2020/11/8 0:02
 */
class ClassB extends ClassA {

  override var num: Int = _ //可以覆写var原理： 父类没有声明这个变量，所以可以在子类中覆写。
  override val num2: Int = 100
  //override var num3: Int = 100

  override def work(): Unit = {
    println("classB work")
    super.work() //显示调用父类的方法。
  }

}


object ClassB {
  def mm(a: Int) = {
    println("mm")
  }

  val m2: Int => Unit = mm _
  val fun1:Int=>String = (a:Int)=>{""}
  def main(args: Array[String]): Unit = {
    val cl = new ClassB()
    cl.work()

    //查看被voerride的属性值。
    println(cl.num)
    m2(1)

    val cl2 : ClassA = new ClassB
    val num: Int = cl2.num
    println(s"使用父类引用，访问子类对象中的变量值是${num}")
  }
}
