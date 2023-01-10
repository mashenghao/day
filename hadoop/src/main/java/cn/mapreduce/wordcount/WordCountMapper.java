package cn.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * mapper类
 *
 * @author: mahao
 * @date: 2020/2/13
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    Text k = new Text();
    IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        System.out.println("key: " + key + " value: " + value);

        // 1. 获取一行
        String len = value.toString();

        //2. 处理数据
        String[] splits = len.split(" ");

        for (String s : splits) {
            k.set(s);

            //3.写出到文件
            context.write(k, v);
        }
    }
}
