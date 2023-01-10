package cn.ETL;

import cn.outputFormat.LogDriver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: mahao
 * @date: 2020/3/1
 */
public class EtlDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[]{"D:\\tmp\\ETL", "D:\\tmp\\ETL\\out1\\"};

        // 1 获取job信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 加载jar包
        job.setJarByClass(EtlDriver.class);

        // 3 关联map
        job.setMapperClass(EtlMapper.class);

        // 4 设置最终输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置reducetask个数为0
        job.setNumReduceTasks(0);

        // 5 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 6 提交
        job.waitForCompletion(true);

    }

    private static class EtlMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        Text k = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // 1 获取1行数据
            String line = value.toString();
            // 2 解析日志
            boolean result = parseLog(line, context);
            // 3 日志不合法退出
            if (!result) {
                return;
            }
            // 4 设置key
            k.set(line);
            // 5 写出数据
            context.write(k, NullWritable.get());
        }

        // 2 解析日志
        private boolean parseLog(String line, Context context) {
            // 1 截取
            String[] fields = line.split(" ");
            // 2 日志长度大于11的为合法
            if (fields.length > 11) {
                // 系统计数器
                context.getCounter("map", "true").increment(1);
                return true;
            } else {
                context.getCounter("map", "false").increment(1);
                return false;
            }
        }

    }
}
