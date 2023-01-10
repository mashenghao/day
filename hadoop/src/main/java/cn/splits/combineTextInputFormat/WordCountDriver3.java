package cn.splits.combineTextInputFormat;

import cn.mapreduce.wordcount.WordCountDriver;
import cn.splits.FileInputFormat.WordCountMapper;
import cn.splits.FileInputFormat.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 切片机制二： combineTextInputFormat方式，对源文件进行虚拟存储然后切分。
 * 他会对小文件进行合并为一个切片。
 * 在源文件中共四个文件，大小是4.56m  1.77m  1.48m 6.22m
 * 如果使用FileInputFormat切片机制，则MapTask会启动四个。会对每个文件启动一个； number of splits:4
 * <p>
 * 如果使用ConbineTextFileInputFormat。将它的最大值划分为4m，则会启动的个数是； 4个
 * 虚拟存储划分： 2.28  2.28  1.77 1.48 3.11 3.11
 * 切片是 （2.28+2.28） （1.77+1.48+3.11） （3.11）三个
 * number of splits:3
 * @author: mahao
 * @date: 2020/2/19
 */
public class WordCountDriver3 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1. 获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar，map与reducer
        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);


        //3.设置map reducer的输出kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //4.设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //使用CombineTextInputFormat，进行切片的处理
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, 4 * 1024 * 1024);
        //5.提交
        job.waitForCompletion(true);
    }
}
