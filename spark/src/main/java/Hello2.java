import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;

/**
 * java 的rdd操作，还是使用的spark的操作。
 * 关于注释的信息，可以通过打印的日志进行查看。
 *
 * @author: mahao
 * @date: 2020/12/22 chapter1.Hello2
 */
public class Hello2 {

    static {
        System.out.println("hello2  初始化");
    }
    //运行的时候，会先计算出stage操作。
    public static void main(String[] args) throws InterruptedException {
        //1. 创建sparkConf对象，设置配置对象，包括要连接的master或者运行模式
        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                //.setMaster("spark://node01:7077") //可以设置spark master的节点位置、
                .setAppName("hello_java"); //spark UI用来显示的。

        //2. sparkcontext用来初始化spark程序的核心组件，包括调度器（DagScheduler和taskScheduler），
        // 还会去master节点上进行注册。 会初始化程序。
        // scala开发使用原生的sparkcontext，这些spark内核的东西。
        JavaSparkContext sc = new JavaSparkContext(conf);

        //3. 用文件初始化一个spark rdd，文件中的数据会被分区，分布到rdd的每个分区
        //中，从而形成一个分布式的数据集。
        //这个案例中，这个文件被分成了两个分区，rdd不同的分区将会在不同的地方进行计算。
        JavaRDD<String> lineRdd = sc.textFile("input/chapter1/word.txt", 2);
        JavaRDD<String> flatMap = lineRdd.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" "));
            }
        });
        JavaPairRDD<String, Integer> mapPairRdd = flatMap.mapToPair((PairFunction<String, String, Integer>) s ->
                new Tuple2<>(s, 1)); //scala中的pairRdd需要隐式转换。
        //这是个窄依赖的操作，在他上面的三个算子，读取文件，flatmap，maptoPair都可以并行运算。
        //这之前是stage0，之后是stage1，一共就两个stage。这些算子在各个executor上运行完毕后，会发送信息给driver，
        //标记着stage0执行完毕了，将会在dagScheduler中移除stage0，之后进行下面的stage操作。

        //  ========================================  //
        //之后的操作，包括下面的shuffle算子与最下面的action算子，共同组成了一个stage1，这里的shuffle算子后的结果，是新的stage的开始，
        //shuffle操作，不参与stage操作。
        JavaPairRDD<String, Integer> reduceRdd = mapPairRdd.reduceByKey((Function2<Integer, Integer, Integer>) (v1, v2) ->
                v1 + v2);
        //这里是行为算子，将会在这里触发计算，因此算着这个算子，组成了一个job。 INFO SparkContext: Starting job: foreach at Hello2.java:53
        reduceRdd.foreach((VoidFunction<Tuple2<String, Integer>>) stringIntegerTuple2 ->
                System.out.println(stringIntegerTuple2._1 + "  " + stringIntegerTuple2._2));
        Thread.sleep(999999);
        sc.close();

    }
}
