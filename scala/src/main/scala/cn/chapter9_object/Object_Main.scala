package cn.chapter9_object

/**
 * main函数的定义2中方式：
 * 1.main函数
 * 2.App的trait接口。
 * App Trait的工作原理为：App Trait继承自DelayedInit Trait，
 * scalac命令进行编译时，会把继承App Trait的object的constructor
 * 代码都放到DelayedInit Trait的delayedInit方法中执行
 *
 * @author: mahao
 * @date: 2020/11/7 21:48
 */
object Object_Main extends App {

  //1.scala中的main方法定义为def main(args: Array[String])，而且必须定义在object中
  override def main(args: Array[String]): Unit = {

    //1.无法访问到声明为私有的object方法。
    //  Object_MethodField.cn$chapter9_object$Object_MethodField$$f1
    Object_MethodField.f2
  }

}
