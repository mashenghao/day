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

/**
 * 求流量用的前十：
 * 自己思路，key按照总流量降序排序，然后reducer阶段取出前十就行了。方案可行，实现方式是在reducer阶段完成的合并，效率慢。
 *
 * @author: mahao
 * @date: 2020/3/9
 */
public class TopNDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[]{"D:\\tmp\\flowSum\\out1\\part-r-00000", "D:\\tmp\\example\\topN\\out1"};

        Configuration config = new Configuration();
        Job job = Job.getInstance(config);

        job.setJarByClass(TopNDriver.class);
        job.setMapperClass(TopNMapper.class);
        job.setReducerClass(TopNReducer.class);

        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class TopNMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

        Text v = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] split = value.toString().split("\t");
            FlowBean k = new FlowBean();
            k.setUpFlow(Integer.parseInt(split[1]));
            k.setDownFlow(Integer.parseInt(split[2]));
            k.setSumFlow(Integer.parseInt(split[3]));
            v.set(split[0]);
            context.write(k, v);
        }
    }

    private static class TopNReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

        int count = 10;

        @Override
        protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            if (count <= 0) {
                return;
            }

            for (Text value : values) {
                context.write(value, key);
                count--;
            }
        }
    }
}
