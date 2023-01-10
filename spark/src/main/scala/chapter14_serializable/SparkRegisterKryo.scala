package chapter14_serializable

import com.esotericsoftware.kryo.io.{Input, Output}
import com.esotericsoftware.kryo.{Kryo, Serializer}
import org.apache.spark.serializer.KryoRegistrator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: mahao
 * @date: 2021/12/16
 */
object SparkRegisterKryo {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("测试注册kryo的序列话类")
      .set("spark.kryo.registrator", "chapter14_serializable.SparkRegisterKryo.MyResister")

    val sc = new SparkContext(sparkConf)


  }

  class MyResister extends KryoRegistrator {
    override def registerClasses(kryo: Kryo): Unit = {
      //      kryo.register(classOf[User], new JavaSerializer)
      kryo.register(classOf[User], new MySerializer) //注册序列化器
    }
  }

  class User(val name: String, val age: Int) {

  }

  class MySerializer extends Serializer[User] {
    override def write(kryo: Kryo, output: Output, os: User): Unit = {
      output.writeString(os.name) //可以用这几种方式去写出，直接用output也可以kryo。
      kryo.writeObject(output, os.age)
    }

    override def read(kryo: Kryo, input: Input, `type`: Class[User]): User = {
      new User(kryo.readObject(input, classOf[String]), input.readInt())
    }
  }

}
