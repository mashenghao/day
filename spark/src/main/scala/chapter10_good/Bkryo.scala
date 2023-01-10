package chapter10_good

import java.io.{ByteArrayOutputStream, ObjectOutputStream}

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Output
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
 * 2.使用高性能序列化类库，kryo。
 *
 * @author: mahao
 * @date: 2021/3/14 21:01
 */
class Person(name: String, age: Int) extends Serializable {
  def this() {
    this(null, 0)
  }

  override def toString: _root_.java.lang.String = {
    name + "  " + age
  }
}

//kryo的序列化与反序列化。
class Bkryo {

  //单独使用kryo的操作。
  @Test
  def kryoDemo: Unit = {
    val p = new Person("张三", 18)
    //1.使用java自己的序列化进行序列化对象
    val javaByteOut: ByteArrayOutputStream = new ByteArrayOutputStream()
    val outputStream = new ObjectOutputStream(javaByteOut)
    outputStream.writeObject(p)
    outputStream.flush()
    val javaObjectArray: Array[Byte] = javaByteOut.toByteArray

    //2.使用kryo的序列化方式进行序列化
    val kryoByteOut: ByteArrayOutputStream = new ByteArrayOutputStream()
    val kryo: Kryo = new Kryo()
    kryo.setReferences(true) //支持循环引用，默认就是为true
    kryo.setRegistrationRequired(false) //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置
    val output: Output = new Output(kryoByteOut)
    kryo.writeClassAndObject(output, p)
    output.flush()
    val kryoObjectArray: Array[Byte] = kryoByteOut.toByteArray
    println(s"java的序列化大小是${javaObjectArray.length}, 使用kryo后的大小是${kryoObjectArray.length}")

    val str: String = KryoUtil.writeToString(p)
    println(str)

    //反序列化
    val value: Person = KryoUtil.readFromByteArray(kryoObjectArray)
    println(value)
  }
}

object Bkryo {
  /*
  测试kryo在 spark中的使用，就用测试内存使用的案例，测试kryo序列化分区数据存储到内存中，与java持久化到磁盘差距大小。
   */
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("Bkryo")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    //1.使用java序列化存储数据，所占用的内存数据一共是1837.6 KB
    //2.使用kryo序列化所占用的空间是 1393.7 KB

    val sc = new SparkContext(sparkConf)
    val lineRdd: RDD[String] = sc.textFile("hdfs://node01:9000/tmp/index-all.html", 2)
    val flatRdd: RDD[String] = lineRdd.flatMap(e => e.split(" "))
    val mapRdd: RDD[(String, Int)] = flatRdd.map(e => (e, 1)).persist(StorageLevel.DISK_ONLY)
    val reduceRdd: RDD[(String, Int)] = mapRdd.reduceByKey((sum, num) => {
      sum + num
    })
    reduceRdd.foreach((e) => {

    })

    Thread.sleep(1000000)
  }


}
