package org.example.bFunction;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.example.Stu;

import java.util.Collections;

/**
 * 合并流
 *
 * @author mash
 * @date 2023/12/30 18:04
 */
public class ConnectStream {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(8);

        DataStreamSource<String> dataStream = env.readTextFile("flink/src/main/resources/stu.txt");

        SingleOutputStreamOperator<Stu> stuStream = dataStream.map(new MapFunction<String, Stu>() {
            @Override
            public Stu map(String value) throws Exception {
                String[] ss = value.split(",");
                return new Stu(Integer.valueOf(ss[0]), ss[1], ss[2], ss[3]);
            }
        });

        SplitStream<Stu> splitStream = stuStream.split(new OutputSelector<Stu>() {
            @Override
            public Iterable<String> select(Stu value) {
                return Collections.singleton(value.getClassId());
            }
        });

        DataStream<Stu> classAStuStream = splitStream.select("a").map(new MapFunction<Stu, Stu>() {
            @Override
            public Stu map(Stu value) throws Exception {
                System.out.println("a:   " + Thread.currentThread().getName() + "  : " + value.getId());
                return value;
            }
        });
        SingleOutputStreamOperator<Tuple2<Integer, String>> tupleStream = splitStream.select("a", "b", "c").map(new MapFunction<Stu, Tuple2<Integer, String>>() {
            @Override
            public Tuple2 map(Stu value) throws Exception {
                System.out.println("abc:   " + Thread.currentThread().getName() + "  :  " + value.getId());
                return new Tuple2(value.getId(), value.getClassId());
            }
        });

        classAStuStream.print();
        tupleStream.print();

        //将两个不同类型的流聚合起来
        SingleOutputStreamOperator<Object> connectStream = classAStuStream.connect(tupleStream).map(new CoMapFunction<Stu, Tuple2<Integer, String>, Object>() {
            @Override
            public Object map1(Stu value) throws Exception {
                Stu.sleep(9999);
                System.out.println("connect stu " + Thread.currentThread().getName() + "  :  " + value.getId());
                return value;
            }

            @Override
            public Object map2(Tuple2<Integer, String> value) throws Exception {
                System.out.println("connect t " + Thread.currentThread().getName() + "  :  " + value.f0.toString());
                return value;
            }
        });


        connectStream.print();


        env.execute();

    }
}
