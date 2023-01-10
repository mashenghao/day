package cn.chapter8_class.classField

import scala.beans.BeanProperty

/**
 *2.class的属性，结合scala生成get，set方法的特点，自定义
 * 实现
 *
 * @author: mahao
 * @date: 2020/11/3 23:43
 */
class Class2 {
  private var Myname = ""

  //创建java风格的setget方法。这里会创建四个方法，scala两个，java风格两个。
  @BeanProperty var age: Int = _

  //方法名不能是Myname，因为会递归了。
  def name: String = Myname

  //方法的set方法。
  def name_$eq(name: String) = Myname = name


}

object MainClass2 {
  def main(args: Array[String]): Unit = {
    val class2 = new Class2
    //之所以可以这样，是因为符合了scala的set get方法的规律.
    val name: String = class2.name
    class2.name = "12"

    //测试get和 set方法
    class2.getAge;
  }
}
