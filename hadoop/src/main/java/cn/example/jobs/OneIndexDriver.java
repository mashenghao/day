package cn.example.jobs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 多job串联，完成倒排索引案例。
 * <p>
 * 案例：
 * 存在很多文件，现在统计的是关键字的数量在每个文件上。
 * 输出的格式是：
 * key1 aa.txt-->2  bb.txt-->2
 * <p>
 * 实现： 这种情况下就需要两次job了，第一次job完成的任务是，统计每个文件中中的关键字的
 * 数量， 第二次job的任务是，都这些数据进行汇总。
 * 同时要注意如何设置key和value。
 * 第一次job： key是 关键字+文件名  value是数量
 * 第二次从第一次结果中，将key拆分成关键字作为key， 文件名和value组成了值。
 *
 * @author: mahao
 * @date: 2020/3/9
 */
public class OneIndexDriver {

    //第一阶段，统计每个文件中的关键字的数量。
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[]{"D:\\tmp\\example\\jobs\\", "D:\\tmp\\example\\jobs\\out1"};

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(OneIndexDriver.class);

        job.setMapperClass(OneIndexMapper.class);
        job.setReducerClass(OneIndexReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

    }

    /**
     * 第一阶段的map是对数据进行拆分和标记，标记每个key所在的文件名。
     * <p>
     * msh--a.txt 1 写成这样的格式，能够分开统计每个文件中的key的数量。
     */
    private static class OneIndexMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        private String fileName;
        Text k = new Text();
        IntWritable v = new IntWritable(1);

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit split = (FileSplit) context.getInputSplit();
            fileName = split.getPath().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String s = value.toString();
            String[] strings = s.split(" ");
            for (String e : strings) {
                k.set(e + "--" + fileName);
                context.write(k, v);
            }
        }
    }

    //统计每个文件的key数据，并将数据写出。写成msh--aa.txt 5
    private static class OneIndexReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        IntWritable v = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum++;
            }
            v.set(sum);

            context.write(key, v);
        }
    }
}
