package cn.chapter8_class.classApply

/**
 * object和class的理解：
 *1.class,下面的代码将会生成的ClassA的代码是：生成的class是class和object的结合体：
 * 对于class，会使用里面的所有方法，会将object中的所有方法，封装成class内部的静态方法。
 * 通过Object的apply方法或者new 出来的对象，都是这个class的实例。
 * public class ClassA { //样例类最终生成的类型
 *
 * //object中定义的方法，最终在class中是静态方法，但是这个静态方法，没有被使用。
 * public static void funO1() {
 * ClassA$.MODULE$.funO1();
 * }
 *
 * //class中定义的apply方法，用来实现()的方法。
 * public void apply(String str) {
 * Predef$.MODULE$.println((new StringBuilder()).append("class ).append(str).toString());
 * }
 *
 * //这个是定义的class的普通，在里面调用了object的funO1方法，查看调用逻辑，就是直接使用Object的
 * //派生类去调用。所有的使用到了Object的方法，都会被调用转换为使用它的派生类.MODULE$.方法去实现。
 * public void funC1() {
 * Predef$.MODULE$.println("我是定义在class中的funC1,调用了Object的funO1方法");
 * ClassA$.MODULE$.funO1();
 * }
 * //class中定义的构造函数。
 * public ClassA()
 * {
 *     Predef..MODULE$.println("ClassA 被初始化");
 * }
 * }
 * 总结： 编译生辰的class内容是由class和object共同组成，最终class会有class的全部内容，里面对object函数的调用，
 * 都会被调换成伴生类去调用他的内部方法。对于object中的方法，在最终class中也会有声明，但是在它创建的对象中是无法使用的。
 *
 * 2. 对于objcet，每个object都会创建一个伴生类，以$结尾，这个里面的方法全是object中定义的方法，但是都是实例方法，在伴生类中
 * 有个静态实例变量，外部想访问这些方法，可以通过 伴生类.MODULE$.方法名，所以，外部通过Object调用的方法，最终都被编译为伴生类去
 * 调用的方式。
 * public final class ClassA$ {
 * public static final ClassA$ MODULE$;//静态变量。
 *
 * public ClassA apply() {
 *     scala.Predef$.MODULE$.println("apply 函数被调用  );
 * ClassA c = new ClassA();
 * return c;
 * }
 *
 * public void funO1() {
 *     scala.Predef$.MODULE$.println("我是定义在object中的funO1);
 * }
 *
 * private ClassA$() {
 * MODULE$ = this;
 * }
 * }。。。
 *
 * 所以，外部通过类名.apply()方法，调用的实际是伴生类中的方法，这个方法没有在最终class中生成一个静态的方法签名，
 * 当实例变量通过apply方法时，使用的是class内部定义的apply方法。这两个apply定义在两个不同的类中，一个是ClassA$中，
 * 用来生成一个ClassA实例，一个定义在ClassA中，用来操作对象。
 *
 * Class$的作用，现在将静态方法独立出来了，可以作为ClassA的一个伴生类，用来辅助ClassA来创建实例对象，调用一些静态方法等。
 *
 * 案例： 在组件中，抽象CMpt组件父类，可以有Object或者Class继承，如果由Class继承，则实现work方法，在生成的MyCmpt.class中，
 * 肯定有work方法，我们就可以通过反射，获取MyCmpt的实例，然后调用方法。 对于Object继承的话，这个work方法的定义肯定在MyCmpt$.class
 * 伴生类中实现了，但是在MyCmpt中，肯定有个他的静态方法签名，通过这个方法签名，最终也是调用的这个实现的方法。除此之外，也可以通过获取
 * MyCmpt$的实例去执行work方法，即获取静态属性MODULE$去执行。
 *
 * @author: mahao
 * @date: 2020/10/27 20:52
 */
class ClassA {

  println("ClassA 被初始化")

  def apply(str: String): Unit = {
    println("class 内部的apply被调用了" + str)
  }

  def funC1(): Unit = {
    println("我是定义在class中的funC1,调用了Object的funO1方法")
    ClassA.funO1()
  }

  def update(str: String, str2: Int): Unit = {
    println(str + " 被修改为： " + str2)
  }

}


object ClassA {
  def apply(): ClassA = {
    println("apply 函数被调用")
    val c = new ClassA()
    c
  }

  def funO1(): Unit = {
    println("我是定义在object中的funO1")
  }

  //object定义的main函数，按照上面的转换规律，也就是在最终class中的main函数，会加上static
  //修饰。
  def main(args: Array[String]): Unit = {
    println("我是main方法")
  }
}
