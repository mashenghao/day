package org.example.bFunction;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.Collections;

/**
 * 将一条流 分成多个流
 * split  和 select
 *
 * @author mash
 * @date 2023/12/30 17:04
 */
public class SplitTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> dataStream = env.readTextFile("src/main/java/org/example/a/hello.txt");
        SingleOutputStreamOperator<String> flatStream = dataStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> collector) throws Exception {
                for (String s : value.split(" ")) {
                    collector.collect(s);
                }
            }
        });

        // 将某个数据打上几个标签，之后可以通过select 获取打上这个标签的数据。
        /**
         * 处理逻辑我理解是:
         *  split算子存储有多少个select 算子， 记住每个select的函数流，
         *  split接受到上游数据后，获取到数据对应的tag，将这个算子输出到对应的select流中。
         *
         *  从flink上来看，每个tag就是不同的流。
         */
        SplitStream<String> splitStream = flatStream.split(new OutputSelector<String>() {
            @Override
            public Iterable<String> select(String s) {
                if (s.contains("fb691e1ad03d6bbaa4a20a6e022a2053")) {
                    try {
                        Thread.sleep(99999L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return Collections.singletonList(s.hashCode() % 2 == 0 ? "t1" : "t2");
            }
        });

        for (String s : Arrays.asList("t1", "t2")) {
            splitStream.select(s).print();
        }

        env.execute();

    }
}
