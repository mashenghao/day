package cn.chapter2_forIf

/**
 * if语句: 带返回值是三元运算符的作用。如果分支中有多行代码，则先进行计算，最后在返回结果。
 * 1. if语句带返回值，可用变量去接收返回值
 * 2. 分支返回值类型不一致时，将向上转型，如Any类型。
 * 3. 反编译知道：if带返回值是三元表达式，而且，如果结果中，有多行代码，会将多行代码，抽出在外边计算。下面的是，测试else不存在的代码。
 * int a = 0;
 * a += flag;
 * int b = 4;
 *
 * Object r3 = flag > 1 ?
 *       BoxesRunTime.boxToInteger(a + b) :
 *       BoxedUnit.UNIT;
 *
 * Object r31 = flag > 1 ?
 *       BoxesRunTime.boxToInteger(2) : BoxedUnit.UNIT;
 *
 * @Date: 2020/10/19 13:28
 * @Author: mahao
 */
class IfDemo {

  //定义class这个method作用是，测试，如果三元表达式中有多行代码，
  //是否也会单独拉出来计算的：
  /*
    public Object m1(int flag)
  {
    int a = 0;
    a += flag;
    int b = 4;
    //会先对表示进行求值，最后才会进行计算。
    Object r3 = flag > 1 ?
      BoxesRunTime.boxToInteger(a + b) :
      BoxedUnit.UNIT;

    return r3;
  }

   */
  def m1(flag: Int): Any = {
    val r3: Any = if (flag > 1) {
      var a = 0
      a += flag
      var b = 4
      a + b
    } else {
      //该返回值的类型是，Unit类型，else不写或者为空代码块
    }
    r3
  }

}

object IfDemo {

  def main(args: Array[String]): Unit = {
    //1.if表达式有返回值
    var flag = 0
    val r1: Int = if (flag > 0) 1 else 0
    println(s"if语句返回值是 ${r1}")

    //2. 如果子句分支的返回值，不一致，返回值将是父类
    flag = 2
    val r2: Any =
      if (flag > 1) {
        val s1 = "分支1"
        flag
      } else ""
    println(s"if语句返回值类型是:${r2.getClass}")

    //3.只有一个分支，如果写了else，代码块为空，返回值为Unit。
    val r3: Any = if (flag > 1) {
      var a = 0
      a += flag
      var b = 4
      a + b
    } else {
      //该返回值的类型是，Unit类型，else不写或者为空代码块
    }
    val r31: Any = if (flag > 1) {
      2
    }
    println(s"if语句测试只有一个分支的情况,值是${r3},类型是：${r3.getClass}")
    //if语句测试只有一个分支的情况,值是(),类型是：class scala.runtime.BoxedUnit
    //if语句测试只有一个分支的情况,值是2,类型是：class java.lang.Integer

    //4.块表达式，就是{ }括起来的值，可以包含多条语句，其中最后一个语句就是表达式的值。
    //上述案例中，if之后的{}就是块表达式，可以将执行结果赋值变量。
    var r4 = {
      var a = 0
      a += 2
      a
    }
    println(s"块表达式的执行结果是：${r4}")

    //5.如果if中，不返回值，则执行结果,这种情况就是使用if - else 。
    /*
     if (flag > 1)
      r4 = 1;
    else
      r4 = 0;
     */
    if (flag > 1) {
      r4 = 1
    } else {
      r4 = 0
    }
  }
}
