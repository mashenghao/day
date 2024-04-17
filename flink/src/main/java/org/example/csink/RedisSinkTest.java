//package org.example.csink;
//
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.connectors.redis.RedisSink;
//import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisConfigBase;
//import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
//import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
//import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
//import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
//import org.example.Stu;
//
///**
// * @author mash
// * @date 2023/12/30 23:29
// */
//public class RedisSinkTest {
//
//
//    public static void main(String[] args) {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(8);
//
//        DataStreamSource<String> dataStream = env.readTextFile("flink/src/main/resources/stu.txt");
//
//        dataStream.print();
//        SingleOutputStreamOperator<Stu> stuStream = dataStream.map(new MapFunction<String, Stu>() {
//            @Override
//            public Stu map(String value) throws Exception {
//                String[] ss = value.split(",");
//                return new Stu(Integer.valueOf(ss[0]), ss[1], ss[2], ss[3]);
//            }
//        });
//
//        FlinkJedisConfigBase base = new FlinkJedisPoolConfig.Builder()
//                .setHost("localhost")
//                .setPort(6379)
//                .build();
//        stuStream.addSink(new RedisSink<>(base, new RedisExampleMapper()));
//    }
//
//    public static class RedisExampleMapper implements RedisMapper<Stu> {
//
//        //stu 是hash结构的主key。
//        public RedisCommandDescription getCommandDescription() {
//            return new RedisCommandDescription(RedisCommand.HSET, "STU");
//        }
//
//        @Override
//        public String getKeyFromData(Stu data) {
//            return "" + data.getId();
//        }
//
//        @Override
//        public String getValueFromData(Stu data) {
//            return data.getName();
//        }
//
//    }
//}
