package org.example.a;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 批处理的world count
 * 1. env.readTestFile()的返回类型是dataset，是有界数据流，无界数据流返回是啥
 * 2. collector是收集当前函数的输出，如何实现的。 如何交付给下一个处理函数的，比如 发flatmap 之后再加map。
 * 3. 有map函数吗，map还是collector吗
 *
 * @author mash
 * @date 2023/12/23 20:51
 */
public class Wc_batch {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //dataSet有界数据
        DataSource<String> dataset = env.readTextFile("src/main/java/org/example/a/hello.txt");

        //dataset切分
        FlatMapOperator<String, String> flatSet = dataset.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                for (String s : value.split(" ")) {
                    out.collect(s);
                }
            }
        });

        //map算子没有collector参数
        MapOperator<String, Tuple2<String, Integer>> tupleSet = flatSet.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                return new Tuple2<>(value, 1);
            }
        });

        //返回类型不再是dataset，而是group对象
        AggregateOperator<Tuple2<String, Integer>> groupSet = tupleSet
                .groupBy(0)
                .sum(1);

        groupSet.print();

    }
}
