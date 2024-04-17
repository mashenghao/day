package org.example.bFunction;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author mash
 * @date 2023/12/30 15:52
 */
public class KeyBy {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStream = env.readTextFile("src/main/java/org/example/a/hello.txt");
        /**
         * flatMap 会将数据放到out中，不明白为啥和map不一样。Collector是会收集完再去放到下一个task中，
         * 还是有特殊处理，每次收集一个就处理一个。
         */
        SingleOutputStreamOperator<String> flatStream = dataStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                //todo: 处理逻辑 放到collector
                for (String s : value.split(" ")) {
                    out.collect(s);
                }
            }
        });

        KeyedStream<String, Tuple> stringTupleKeyedStream = flatStream.keyBy("22");

        //分组之后，返回了一个特殊的stream， stram中有聚合函数可以传进去。 泛型是key的类型
        KeyedStream<String, Integer> keyedStream = flatStream.keyBy(new KeySelector<String, Integer>() {
            @Override
            public Integer getKey(String value) {
                return value.hashCode();
            }
        });
        //为每个key下相同的key进行分组聚合操作。
//        keyedStream.max()
        keyedStream.reduce(new ReduceFunction<String>() {
            @Override
            public String reduce(String value1, String value2) throws Exception {
                return value1 + value2;
            }
        });

        env.execute();

    }
}
