package cn.example.topN;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.*;

/**
 * topN实现方式二：
 * 通过在map端计算出topN，只是传输topN到reducer，即可了。
 *
 * @author: mahao
 * @date: 2020/3/9
 */
public class TopNDriver2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[]{"D:\\tmp\\flowSum\\out1\\part-r-00000", "D:\\tmp\\example\\topN\\out2"};

        Configuration config = new Configuration();
        Job job = Job.getInstance(config);

        job.setJarByClass(TopNDriver2.class);
        job.setMapperClass(TopNMapper2.class);
        job.setReducerClass(TopNReducer2.class);

        job.setMapOutputKeyClass(FlowBean2.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean2.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    //map端只记录前十的数据。
    private static class TopNMapper2 extends Mapper<LongWritable, Text, FlowBean2, Text> {


        // 定义一个TreeMap作为存储数据的容器（天然按key排序）
        TreeMap<FlowBean2, Text> map = new TreeMap<FlowBean2, Text>();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] split = value.toString().split("\t");

            FlowBean2 k = new FlowBean2();
            k.setUpFlow(Integer.parseInt(split[1]));
            k.setDownFlow(Integer.parseInt(split[2]));
            k.setSumFlow(Integer.parseInt(split[3]));
            Text v = new Text();
            v.set(split[0]);

            map.put(k, v);
            if (map.size() > 10) {
                map.remove(map.lastKey());
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Map.Entry<FlowBean2, Text> e : map.entrySet()) {

                context.write(e.getKey(), e.getValue());
            }

        }
    }

    private static class TopNReducer2 extends Reducer<FlowBean2, Text, Text, FlowBean2> {

        // 定义一个TreeMap作为存储数据的容器（天然按key排序）
        TreeMap<FlowBean2, Text> map = new TreeMap<>();

        @Override
        protected void reduce(FlowBean2 key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                FlowBean2 bean2 = new FlowBean2();
                bean2.setDownFlow(key.getDownFlow());
                bean2.setUpFlow(key.getUpFlow());
                bean2.setSumFlow(key.getSumFlow());

                map.put(bean2, new Text(value.toString()));
                // 2 限制TreeMap数据量，超过10条就删除掉流量最小的一条数据
                if (map.size() > 10) {
                    map.remove(map.lastKey());
                }

            }

        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            // 3 遍历集合，输出数据
            Iterator<FlowBean2> it = map.keySet().iterator();

            while (it.hasNext()) {

                FlowBean2 v = it.next();

                context.write(new Text(map.get(v)), v);
            }

        }
    }
}
