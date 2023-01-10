package chapter2

import java.util.concurrent.atomic.AtomicInteger

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * 广播变量与累加变量：
 * jvm级别的counter，无法在分区之间共享，因为他们属于不同的task，
 * 可能在不同的jvm上，所以无法互相访问。如果想要实现累加变量，可以通过
 * spark提供的累加变量支持。
 *
 * @author: mahao
 * @date: 2020/12/30 0:44
 */
object IBoradvar {
  val conf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("广播变量")

  val sc = new SparkContext(conf)
  val counter = new AtomicInteger(0)

  def main(args: Array[String]): Unit = {
    //1. 验证在driver端的变量，在算子中使用，
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 8)
    val mapRdd: RDD[Int] = rdd.map(e => {
      Thread.sleep(100)
      val i: Int = counter.getAndIncrement()
      println(Thread.currentThread().getName + " - " + counter.hashCode() + "  -- ->  " + i)
      e
    })
    mapRdd.count()
    //这个counter结果是正确的，可能是因为不是分布式部署，才会执行正确的。
    //在同一个jvm所以共享了这个变量。hashcode的值是不一致的，为什么会数据一样呢？
    println("最后counter结果为： " + counter.hashCode() + "   " + counter.get())

    //2. 广播变量的使用，小表数据广播，map阶段进行join，避免shuffle
    /*
    案例： 输出学生信息
     */
    class Stu(val name: String, val roomNo: Int) extends Serializable
    class Room(val roomNo: Int, val roomName: String) extends Serializable

    val rooms = Seq(new Room(1, "1班"), new Room(2, "2班"), new Room(3, "3班"))
    val stuRdd = sc.makeRDD(for (i <- 1 to 1000) yield new Stu("name" + i, i % 3 + 1), 3)
    val roomRdd = sc.makeRDD(rooms, 3)
    //直接使用join操作，获取他们的关联信息
    val mapStruRdd: RDD[(Int, Stu)] = stuRdd.map(e => (e.roomNo, e))
    val mapRoomRdd: RDD[(Int, Room)] = roomRdd.map(e => (e.roomNo, e))
    /**
     * 使用两个rdd进行join,
     * foreach触发了第二个job任务，编号为job1，输出分区数为3，（3是从触发rdd哪儿获取的），
     * 最终的stage编号为stage3，之前的是stage0，所以一共有三个stage触发了，stage1 makeRdd到map,stage2 makeRdd到map，stage3 leftjoin到foreach，
     * join的算子，这里会等待stage1,2运行完毕，然后在运行，因为那是他的依赖。
     */
    val joinRdd: RDD[(Int, (Stu, Option[Room]))] = mapStruRdd.leftOuterJoin(mapRoomRdd) //使用coGroup算子实现的。
    joinRdd.foreach(e => {
      println(e._2._1.name + " " + e._1 + " " + e._2._2.getOrElse(new Room(0, "")).roomName)
    })

    println("、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、")
    /**
     * 使用广播变量，将rooms广播到各个节点上，直接在map中使用：
     * 这个是job2编号，这个job的最终stage的编号是4，只有一个stage，
     */
    val broadcast: Broadcast[Seq[Room]] = sc.broadcast(rooms)
    val broadRdd: RDD[(String, Int, String)] = stuRdd.mapPartitions(it => {
      val rs: Seq[Room] = broadcast.value
      val map = new mutable.HashMap[Int, Room]()
      rs.foreach(e => map.put(e.roomNo, e))

      val list = new ArrayBuffer[(String, Int, String)]()
      while (it.hasNext) {
        val stu: Stu = it.next()
        val option: Option[Room] = map.get(stu.roomNo)
        list.+=((stu.name, stu.roomNo, option.getOrElse(new Room(0, "")).roomName))
      }
      list.iterator
    })
    broadRdd.foreach(println(_))
    Thread.sleep(3000000)

  }
}
