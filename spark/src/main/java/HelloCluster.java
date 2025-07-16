import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FilterFileSystem;
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
import java.util.List;

/**
 * 在spark集群上进行运行。
 *
 * @author: mahao
 * @date: 2020/12/22 chapter1.Hello2
 */
public class HelloCluster {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                //.setMaster("local[*]") //将这个去掉，会自己查找。
                .setAppName("hello_cluster"); //spark UI用来显示的。

        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lineRdd = sc.textFile("hdfs://node01:9000/user/hadoop/start-all.sh", 3);
        JavaRDD<String> flatMap = lineRdd.flatMap((FlatMapFunction<String, String>) s ->
                Arrays.asList(s.split(" ")));
        JavaPairRDD<String, Integer> mapPairRdd = flatMap.mapToPair((PairFunction<String, String, Integer>) s ->
                new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> reduceRdd = mapPairRdd.reduceByKey((Function2<Integer, Integer, Integer>) (v1, v2) ->
                v1 + v2);
        reduceRdd.foreach((VoidFunction<Tuple2<String, Integer>>) stringIntegerTuple2 ->
                System.out.println(stringIntegerTuple2._1 + "  " + stringIntegerTuple2._2));

        List<Tuple2<String, Integer>> list = reduceRdd.collect();
        System.out.println("=======================任务运行结束:在driver端打印结果是：===========================\n" + list);
        sc.close();
    }
}
