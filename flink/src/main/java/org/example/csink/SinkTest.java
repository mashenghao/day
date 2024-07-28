package org.example.csink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.util.Collector;

/**
 * @author mash
 * @date 2024/4/28 下午10:48
 */
public class SinkTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        System.out.println(123);
        DataStreamSource<String> stream = env.socketTextStream("localhost", 8888);

        SingleOutputStreamOperator<Tuple2<String, Integer>> flatMap = stream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                for (String s : value.split(" ")) {
                    out.collect(new Tuple2<>(s, 1));
                }
            }
        });

        SingleOutputStreamOperator<Tuple2<String, Integer>> sumStream = flatMap.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> value) throws Exception {
                return value.f0;
            }
        }).flatMap(new FlatMapFunction<Tuple2<String, Integer>, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(Tuple2<String, Integer> value, Collector<Tuple2<String, Integer>> out) throws Exception {
                out.collect(value);
            }
        });

        //每次输出数据就执行一次
        sumStream.print();
        sumStream.addSink(new PrintSinkFunction<>());
        sumStream.addSink(new PrintSinkFunction<>(true));


        env.execute();
    }
}
