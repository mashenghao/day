package cn.chapter8_class.classObjectClass

import chapter8_class.AbsClassI


/**
 *1. 创建一个类，去实现抽象方法，在java中通过反射获取他的class，然后获取方法调用。
 *
 * public class MyClass extends AbsClassI {
 * public void work() {
 * Predef$.MODULE$.println("我是class 实现的方法。");
 * }
 * }
 * 可以直接通过类名的方式获取，并且能够得到父类的方法。
 *
 * @author: mahao
 * @date: 2020/10/28 0:04
 */
class MyClass extends AbsClassI {

  override def work(): Unit = {
    println("我是class 实现的方法。")
  }
}
