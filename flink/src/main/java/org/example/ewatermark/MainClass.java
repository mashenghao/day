package org.example.ewatermark;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;
import org.example.EventLog;

/**
 * nc -lk 8888
 * <p>
 * 1.
 * watermark 是一个在数据流中特殊的数据元素。 watermark是一个单调递增的数据，当task处理这个 watermark对应的时间时，
 * 标记着watermark之前的窗口就该关闭了。 watermark标识的是窗口关闭的时刻，处理这个元素，标识窗口关闭。
 * <p>
 * 比如:  6 3 2 (2) 5 4 1 这个时间序列数据,  水位应该是3 (5-2),当处理时间5时，应该等待3秒，等待2秒时刻数据来到后，再去关闭窗口，不然2秒数据
 * 就没有地方处理了
 * <p>
 * <p>
 * 2. watermark传递
 * a. 单个task数据， 打散到多个下游task，需要将watermark广播给每个task
 * b. 下游task接收上游多个task的watermark，会将最小的watermark作为这个task的时间，表示这个时间之前的数据在各个分区都已经处理结束了
 * <p>
 * 3.
 *
 * @author mash
 * @date 2024/1/9 22:58
 */
public class MainClass {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置处理的时间 为事件时间
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        DataStreamSource<String> stream = env.socketTextStream("localhost", 8888);
        SingleOutputStreamOperator<EventLog> logStream = stream.map(new MapFunction<String, EventLog>() {
            @Override
            public EventLog map(String value) {
                String[] split = value.split(",");
                return new EventLog(split[0], split[1], Long.parseLong(split[2]));
            }
        });

        //设置数据的延迟时间和事件时间
        SingleOutputStreamOperator<EventLog> watermarkStream = logStream.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<EventLog>(Time.seconds(2)) { //指定延迟
            @Override
            public long extractTimestamp(EventLog element) {
                return element.getTime() * 1000; //指定事件时间
            }
        });

        OutputTag<EventLog> outputTag = new OutputTag<EventLog>("later") {
        };

        SingleOutputStreamOperator<EventLog> agg = watermarkStream.keyBy("id")
                .timeWindow(Time.seconds(5)) //5秒一个窗口 加上之前2秒延迟，允许时间时间内7秒内数据进行聚合
                .allowedLateness(Time.seconds(10)) //允许迟到10秒数据
                .sideOutputLateData(outputTag)
                .minBy("time");

        watermarkStream.print("wm");

        agg.print("agg");

        agg.getSideOutput(outputTag).print("later");

        env.execute();
    }

}
