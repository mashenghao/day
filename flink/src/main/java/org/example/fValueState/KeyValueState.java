package org.example.fValueState;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * KeyValueState的算子必须在keyby之后，获取的值，是分区key的值对应的存储值。
 * 因为所有相同的key都会被分配到一个task中,所以不存在两个task有相同的key。
 * <p>
 * 有一个问题,状态后端如何得知具体的key的值。
 *
 * @author mash
 * @date 2024/1/10 22:41
 */
public class KeyValueState {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> stream = env.socketTextStream("localhost", 8888);

        SingleOutputStreamOperator<Tuple2<String, Integer>> flatMap = stream.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                String[] split = value.split(" ");
                return new Tuple2<>(split[0], Integer.valueOf(split[1]));
            }
        });

        SingleOutputStreamOperator<Tuple3<String, Integer, Integer>> operator = flatMap.keyBy(0)
                .flatMap(new RichFlatMapFunction<Tuple2<String, Integer>, Tuple3<String, Integer, Integer>>() {
                    ValueState<String> state;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        super.open(parameters);
                        state = getRuntimeContext().getState(new ValueStateDescriptor<String>("key-last-value", String.class));
                    }

                    @Override
                    public void flatMap(Tuple2<String, Integer> value, Collector<Tuple3<String, Integer, Integer>> out) throws Exception {
                        String value1 = state.value();
                        if (value1 == null) {
                            value1 = "";
                        }

                        System.out.println(value1);
                        state.update(value1 + "," + value.f0 + ":" + value.f1);
                        out.collect(new Tuple3<>(value1 + "," + value.f0 + ":" + value.f1, 1, 1));
                    }
                });


        //每次输出数据就执行一次
        operator.print();


        env.execute();
    }
}
