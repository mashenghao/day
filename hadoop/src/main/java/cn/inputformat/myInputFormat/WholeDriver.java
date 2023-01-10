package cn.inputformat.myInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * 这个案例是学习自定义InputFormat实现一次读取一个整文件，
 * 将key是文件的全路径名，value是文件的字节。这是在map阶段的
 * 作用，在reducer阶段，则需要把这些文件value合并到同一个文件中，
 * 需要使用输出类`SequenceFileOutPutFormat。
 *
 * @author: mahao
 * @date: 2020/2/20
 */
public class WholeDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(WholeDriver.class);
        job.setMapperClass(WholeMapper.class);
        job.setReducerClass(WholeReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //设置输入和输出的Format类
        job.setInputFormatClass(WholeInputFormat.class);
        job.setOutputValueClass(SequenceFileOutputFormat.class);

        job.waitForCompletion(true);
    }

    public static class WholeMapper extends Mapper<Text, BytesWritable, Text, BytesWritable> {
        @Override
        protected void map(Text key, BytesWritable value, Context context) throws IOException, InterruptedException {
            context.write(key, value);
        }
    }

    public static class WholeReducer extends Reducer<Text, BytesWritable, Text, BytesWritable> {
        @Override
        protected void reduce(Text key, Iterable<BytesWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, values.iterator().next());
        }
    }
}
