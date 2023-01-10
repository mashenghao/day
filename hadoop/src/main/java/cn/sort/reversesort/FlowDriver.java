package cn.sort.reversesort;
 
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
 * 按照总流量的大小，逆序输出结果集。
 * 实现思路：
 * 重写key的compareTo方法
 *
 * @author: mahao
 * @date: 2020/2/26
 */
public class FlowDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{"D:/tmp/sort/reversesort/", "D:/tmp/sort/reversesort/out1"};

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(FlowDriver.class);
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        boolean b = job.waitForCompletion(true);
        System.out.println(b);
    }

    /**
     * map负责写出数据，key是flowbean，value是手机号
     */
    private static class FlowMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

        FlowBean k = new FlowBean();
        Text v = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String s = value.toString();
            String[] split = s.split("\t");
            v.set(split[0]);
            k.setUpFlow(Integer.parseInt(split[1]));
            k.setDownFlow(Integer.parseInt(split[2]));
            k.setSumFlow(Integer.parseInt(split[3]));

            context.write(k, v);
        }
    }

    private static class FlowReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

        @Override
        protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                context.write(value, key);
            }
        }
    }
}
