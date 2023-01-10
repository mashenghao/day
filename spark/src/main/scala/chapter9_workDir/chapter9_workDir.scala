package chapter9_workDir

import java.io.{BufferedReader, File, FileInputStream, InputStreamReader}

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 会通过参数spark.yarn.dist.files和--files分别上传两个文件，并且会在executor和driver中进行
 * 读取这两个文件。 上面这两个参数，会将文件发送到各个executor中，我们直接使用相对目录，就可以读取到文件。
 * 文件们都是在相对文件的位置。
 *
 * @author: mahao
 * @date: 2021/3/8 17:21
 */
object chapter9_workDir {

  val filepath1: String = "./a.txt"
  val filepath2 = "./b.txt"

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      //.setMaster("spark://")
      .setAppName("chapter9_workDir")

    println("////////////////driver端日志打印 start////////////////////")
    val file1 = new File(filepath1)
    println(s"file1的文件绝对路径是${file1.getAbsolutePath}")
    val read = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
    println("file1的内容是：" + read.readLine())
    read.close();
    println("////////////////driver端日志打印 end////////////////////")

    val sc = new SparkContext(conf)
    sc.makeRDD(1 to 20, 2).foreach(e => {
      println("////////////////executor端日志打印 start////////////////////")
      val file2 = new File(filepath2)
      println(s"file2的文件绝对路径是${file2.getAbsolutePath}")
      val read = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
      println("file2的内容是：" + read.readLine())
      read.close();
      println("////////////////executor端日志打印 end////////////////////")
      println(e)
    })

    sc.stop()
  }
}
