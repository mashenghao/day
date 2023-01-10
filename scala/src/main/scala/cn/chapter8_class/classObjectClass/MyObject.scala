package cn.chapter8_class.classObjectClass

import chapter8_class.AbsClassI

/**
 * 2.Object实现抽象方法，验证直接通过MyObject类，能否获取work方法，
 * 如果不行，则通过获取他的伴生类获取。
 *
 * //最终类中无法获取到，因为没有继承父类的方法，伴生类继承了父类的方法。
 * public final class MyObject {
 * public static void work() {
 * MyObject$.MODULE$.work();
 * }
 * }
 *
 * //这个里面是有的，继承了方法。
 * public final class MyObject$ extends AbsClassI {
 * public static final MyObject$ MODULE$;
 *
 * private MyObject$() {
 * MODULE$ = this;
 * }
 *
 * public void work() {
 *     scala.Predef$.MODULE$.println("我是object 创建的方法。);
 * }
 * }
 *
 * @author: mahao
 * @date: 2020/10/28 0:06
 */
object MyObject extends AbsClassI {

  override def work(): Unit = {
    println("我是object 创建的方法。")
  }
}
