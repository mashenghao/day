package cn.mapreduce.flowsum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 手机流量统计程序驱动类，练习序列化的使用
 *
 * @author: mahao
 * @date: 2020/2/14
 */
public class FlowSumDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{"D:\\tmp\\flowSum\\", "D:\\tmp\\flowSum\\out1\\"};
        //1. 封装Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2. 指定程序jar包所在的本地路径
        job.setJarByClass(FlowSumDriver.class);

        //3.指定本业务job要使用的mapper和reducer
        job.setMapperClass(FlowSumMapper.class);
        job.setReducerClass(FlowSumReducer.class);

        //4. 指定mapper和reducer输出数据的k v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //5.指定job的输入原始文件所在的目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //6.提交
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
