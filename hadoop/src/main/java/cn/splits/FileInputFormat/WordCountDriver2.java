package cn.splits.FileInputFormat;

import cn.mapreduce.wordcount.WordCountDriver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Cluster;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 切片机制一： FileInputFormat的切分机制
 * 通过wordCount案例查看划分的切片个数。
 * 存在两个文件：f1 320m ； f2 10m
 * 切分后，形成四个切片：
 * f1.split1 - 0 128
 * f1.split2 - 128-256
 * f1.split3 - 256 320
 * f2.split1 - 0 10
 *
 * @author: mahao
 * @date: 2020/2/19
 */
public class WordCountDriver2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        //1. 获取job
        Configuration conf = new Configuration();
        conf.setLong(FileInputFormat.SPLIT_MINSIZE,128*1024*1024);
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
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //设定切片的大小为128m
        //指定split的大小，本地模式下是32m，要改为128m
        FileInputFormat.setMaxInputSplitSize(job,128*1024*1024);
        //5.提交
        job.waitForCompletion(true);
    }
}
