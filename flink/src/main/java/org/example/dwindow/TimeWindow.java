package org.example.dwindow;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;
import org.example.RandomSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 窗口分为2中类型：
 * 1.时间窗口- 当时间到了指定规则后，将这个时间内的事件都划分到一个时间桶中
 * a. 滚动时间窗口  每间隔多少时间，产生一个桶，数据没有重叠。 理解也是维护了一个窗口的数组，当新窗口到达时，删掉后一个窗口。
 * b. 滑动时间窗口  滑动创建一个桶，桶中的数据包含之前滑动的数据。  理解是保存了一个窗口的滑动数组，slide之后，移除slide之后的数据，然后将移除的数据用于reduce操作
 * c. 会话窗口  指定事件之间间隔，指定gap时间内，没收到数据就划分到一个新的窗口中。
 * <p>
 * 2. 计数窗口 - 当事件积攒够指定数量后，将事件划定到桶中。
 * <p>
 * <p>
 * <p>
 * 时间窗口流，将桶作为新的事件类型，获取的是桶中的数据。
 *
 * @author mash
 * @date 2024/1/4 21:57
 */
public class TimeWindow {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataStream = env.addSource(new RandomSource());

        KeyedStream<String, String> keyedStream = dataStream.keyBy((KeySelector<String, String>) value -> (Long.valueOf(value) % 10) + "");
        SingleOutputStreamOperator<String> reduce = keyedStream.reduce(new ReduceFunction<String>() {
            @Override
            public String reduce(String value1, String value2) throws Exception {
                return value1 + "," + value2;
            }
        });

//        reduce.print("正常分组输出流");
        //滚动时间窗口,只是聚合当前窗口内数据，之前的数据是不会保存的。
        SingleOutputStreamOperator<String> timeStream = keyedStream.timeWindow(Time.seconds(6)).aggregate(new AggregateFunction<String, List<String>, String>() {

            @Override
            public List<String> createAccumulator() {
                return new ArrayList<>();
            }

            @Override
            public List<String> add(String value, List<String> accumulator) {
                accumulator.add(value);
                return accumulator;
            }

            @Override
            public String getResult(List<String> accumulator) {
                return String.join(",", accumulator);
            }

            @Override
            public List<String> merge(List<String> a, List<String> b) {
                a.addAll(b);
                return a;
            }
        });
        /*
        正常分组输出流:5> 5,15,25,35,45,55,65
        正常分组输出流:5> 8,18,28,38,48,58

        //1. 滚动窗口聚合也只是这指定窗口内数据，不会对之前的进行保存的
        滚动窗口输出:7> 9,19,29
        滚动窗口输出:7> 39,49,59,69,79,89
         */
        timeStream.print("滚动窗口输出");


        SingleOutputStreamOperator<String> slideStream = keyedStream.timeWindow(Time.seconds(6), Time.seconds(2)).reduce(new ReduceFunction<String>() {
            @Override
            public String reduce(String value1, String value2) throws Exception {
                return value1 + "," + value2;
            }
        });
        /*
        正常分组输出流:5> 5,15,25,35,45,55,65
        正常分组输出流:5> 8,18,28,38,48,58

        //1. 滚动窗口聚合也只是这指定窗口内数据，不会对之前的进行保存的。 理解也是维护了一个窗口的数组，当新窗口到达时，删掉后一个窗口。
        滚动窗口输出:7> 9,19,29
        滚动窗口输出:7> 39,49,59,69,79,89


        //2. 滑动窗口的输出。理解是保存了一个窗口的滑动数组，slide之后，移除slide之后的数据，然后将移除的数据用于reduce操作
        滑动窗口输出:3> 3,13
        滑动窗口输出:3> 3,13,23
        滑动窗口输出:3> 3,13,23,33,43
        滑动窗口输出:3> 23,33,43,53,63
        滑动窗口输出:3> 33,43,53,63,73,83
        滑动窗口输出:3> 53,63,73,83,93,103
         */
//        slideStream.print("滑动窗口输出");

        SingleOutputStreamOperator<String> gapStream = keyedStream.window(ProcessingTimeSessionWindows.withGap(Time.seconds(2))).reduce((ReduceFunction<String>) (value1, value2) -> value1 + "," + value2);

        gapStream.print("gap输出流");


        //
        SingleOutputStreamOperator<String> countStream = keyedStream.countWindow(5).reduce((ReduceFunction<String>) (value1, value2) -> value1 + "," + value2);
        countStream.print("每隔5个输出一下");

        env.execute();

    }
}
