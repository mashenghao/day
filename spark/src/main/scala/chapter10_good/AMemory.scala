package chapter10_good

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

/**
 * spark性能优化，诊断内存使用量。
 * 1.设置rdd的并行度，就可以知道该rdd应有几个分区。
 * 2.调用rdd的cache方法，就会将rdd中分区数据，缓存到内存中。
 * 3.观察driver与executor的日志，即可看到blockManager的使用量。
 *
 * @author: mahao
 * @date: 2021/3/13 22:07
 */
object AMemory {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("AMemory")
    val sc = new SparkContext(sparkConf)
    val lineRdd: RDD[String] = sc.textFile("hdfs://node01:9000/tmp/index-all.html", 2)
    val flatRdd: RDD[String] = lineRdd.flatMap(e => e.split(" "))
    //flatRdd调用cache方法，这是还有5个分区，即可在每个分区内缓存数据。
    /* 调用cache后，会对rdd计算后的数据，进行缓存到blockmanager中，blockInfo的id是rdd_3_0 分区号，2.9M的内存。
    21/03/13 22:46:48 INFO MemoryStore: Block rdd_3_0 stored as values in memory (estimated size 2.9 MB, free 2.9 GB)
    21/03/13 22:46:48 INFO BlockManagerInfo: Added rdd_3_0 in memory on localhost:53438 (size: 2.9 MB, free: 2.9 GB)
    21/03/13 22:46:48 INFO BlockManagerMaster: Updated info of block rdd_3_0
    21/03/13 22:46:48 INFO MemoryStore: ensureFreeSpace(2844410) called with curMem=3200998, maxMem=3077465702
    21/03/13 22:46:48 INFO MemoryStore: Block rdd_3_1 stored as values in memory (estimated size 2.7 MB, free 2.9 GB)
    21/03/13 22:46:48 INFO BlockManagerInfo: Added rdd_3_1 in memory on localhost:53438 (size: 2.7 MB, free: 2.9 GB)
    21/03/13 22:46:48 INFO BlockManagerMaster: Updated info of block rdd_3_1
    这个也可以在UI的storage中看到数据。Storage Level是MEMORY_ONLY，可以调用MEMORY_ONLY_SER进行序列化省略内存。
    21/03/13 23:06:34 INFO MemoryStore: Block rdd_3_0 stored as bytes in memory (estimated size 914.8 KB, free 2.9 GB)
    21/03/13 23:06:34 INFO BlockManagerInfo: Added rdd_3_1 in memory on localhost:54010 (size: 922.8 KB, free: 2.9 GB)
    21/03/13 23:06:34 INFO BlockManagerMaster: Updated info of block rdd_3_1
    21/03/13 23:06:34 INFO BlockManagerInfo: Added rdd_3_0 in memory on localhost:54010 (size: 914.8 KB, free: 2.9 GB)
    21/03/13 23:06:34 INFO BlockManagerMaster: Updated info of block rdd_3_0
     */
    val mapRdd: RDD[(String, Int)] = flatRdd.map(e => (e, 1)).persist(StorageLevel.MEMORY_AND_DISK_2)
    val reduceRdd: RDD[(String, Int)] = mapRdd.reduceByKey((sum, num) => {
      sum + num
    })
    reduceRdd.foreach((e) => {

    })
    println("////////////////////////////////////////////////////////////////////")
    //如果maprdd没有调用cache方法，还是会走之前的逻辑，再去提交一次任务的。在此从hadoop中拉取数据，如果开启后，就会走
    //缓存了，直接在分区缓存中拉取数据了。
    //开启cache后的运行日志，看到taskScheduler的tasklocation位置是PROCESS_LOCAL，存在父rdd中的数据在同一个jvm中。
    /*
    21/03/13 22:55:56 INFO DAGScheduler: Submitting 2 missing tasks from Stage 2 (MapPartitionsRDD[3] at map at AMemory.scala:35)
    21/03/13 22:55:56 INFO TaskSchedulerImpl: Adding task set 2.0 with 2 tasks
    21/03/13 22:55:56 INFO TaskSetManager: Starting task 0.0 in stage 2.0 (TID 4, localhost, PROCESS_LOCAL, 1301 bytes)
    21/03/13 22:55:56 INFO TaskSetManager: Starting task 1.0 in stage 2.0 (TID 5, localhost, PROCESS_LOCAL, 1301 bytes)
    21/03/13 22:55:56 INFO Executor: Running task 0.0 in stage 2.0 (TID 4)
    21/03/13 22:55:56 INFO Executor: Running task 1.0 in stage 2.0 (TID 5)
    21/03/13 22:55:56 INFO BlockManager: Found block rdd_3_0 locally
    21/03/13 22:55:56 INFO BlockManager: Found block rdd_3_1 locally
    21/03/13 22:55:56 INFO Executor: Finished task 1.0 in stage 2.0 (TID 5). 1792 bytes result sent to driver
    21/03/13 22:55:56 INFO Executor: Finished task 0.0 in stage 2.0 (TID 4). 1792 bytes result sent to driver
     */
    //没有开启cache的日志。可以看到task的本地化是在ANY,运行是在任何executor上都可以。
    /*
    21/03/13 22:59:15 INFO TaskSchedulerImpl: Adding task set 2.0 with 2 tasks
    21/03/13 22:59:15 INFO TaskSetManager: Starting task 0.0 in stage 2.0 (TID 4, localhost, ANY, 1301 bytes)
    21/03/13 22:59:15 INFO TaskSetManager: Starting task 1.0 in stage 2.0 (TID 5, localhost, ANY, 1301 bytes)
    21/03/13 22:59:15 INFO Executor: Running task 1.0 in stage 2.0 (TID 5)
    21/03/13 22:59:15 INFO Executor: Running task 0.0 in stage 2.0 (TID 4)
    21/03/13 22:59:15 INFO HadoopRDD: Input split: hdfs://node01:9000/tmp/index-all.html:579526+579526
    21/03/13 22:59:15 INFO HadoopRDD: Input split: hdfs://node01:9000/tmp/index-all.html:0+579526
    21/03/13 22:59:15 INFO Executor: Finished task 1.0 in stage 2.0 (TID 5). 1792 bytes result sent to driver
    21/03/13 22:59:15 INFO Executor: Finished task 0.0 in stage 2.0 (TID 4). 1792 bytes result sent to driver
    21/03/13 22:59:15 INFO TaskSetManager: Finished task 1.0 in stage 2.0 (TID 5) in 125 ms on localhost (1/2)
    21/03/13 22:59:15 INFO TaskSetManager: Finished task 0.0 in stage 2.0 (TID 4) in 127 ms on localhost (2/2)
     */
    mapRdd.foreach((e) => {

    })
    Thread.sleep(1000000)
  }
}
