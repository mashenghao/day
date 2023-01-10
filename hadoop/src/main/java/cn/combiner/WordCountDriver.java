package cn.combiner;

import cn.mapreduce.wordcount.WordCountMapper;
import cn.mapreduce.wordcount.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 使用wordcount案例，查看使用Combiner和未使用的区别。
 * 通过日志查看：
 *
 * @author: mahao
 * @date: 2020/2/27
 */
public class WordCountDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        args = new String[]{"D:\\tmp\\combiner\\", "D:\\tmp\\combiner\\out1\\"};

        //1.获取配置信息，以及封装任务
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        //2.设置jar加载路径
        job.setJarByClass(WordCountDriver.class);

        //3.设置map和reducer类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //4.设置map输出的泛型类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //5.设置reducer输出的泛型类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //6.设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //使用combiner机制，完成map端提前汇总，减少网络传输需要。
        job.setCombinerClass(WordCountReducer.class);


        //7.提交
        boolean b = job.waitForCompletion(true);

        System.exit(b ? 0 : 1);
    }
}
