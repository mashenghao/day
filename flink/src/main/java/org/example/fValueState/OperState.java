package org.example.fValueState;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *
 *OperState 状态就是每个分区维护一个变量，flink负责维护下快照与恢复。
 *
 * @author mash
 * @date 2024/1/10 23:23
 */
public class OperState {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> stream = env.socketTextStream("localhost", 8888);

        SingleOutputStreamOperator<Tuple2<String, Integer>> flatMap = stream.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                String[] split = value.split(" ");
                return new Tuple2<>(split[0], Integer.valueOf(split[1]));
            }
        }).setParallelism(4);

        SingleOutputStreamOperator<Integer> mapped = flatMap.map(new MyMapper());


        //每次输出数据就执行一次
        mapped.print();


        env.execute();
    }
}
