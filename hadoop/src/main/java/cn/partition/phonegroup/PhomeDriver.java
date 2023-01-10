package cn.partition.phonegroup;

import cn.mapreduce.flowsum.FlowBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 将手机号码按照不同的号段输出到不同的文件夹中。
 *
 * @author: mahao
 * @date: 2020/2/26
 */
public class PhomeDriver {

    /**
     * 需要的操作是：
     * 1.指定InputFormat使用的类
     * 2.指定使用的Partitioner类
     * 3.指定numReducerTask的数量。
     *
     * @param args
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{"D:\\tmp\\partition\\", "D:\\tmp\\partition\\out1\\"};

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(PhomeDriver.class);
        job.setMapperClass(PhoneMapper.class);
        job.setReducerClass(PhoneReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //1.指定InputFormat使用的类
        job.setInputFormatClass(FlowBeanInputFormat.class);
        //2.指定使用的Partitioner类
        job.setPartitionerClass(PhonePrefixPartitioner.class);
        //3.指定numReducerTask的数量。
        job.setNumReduceTasks(2);

        boolean b = job.waitForCompletion(true);
        System.out.println(b);
    }

    /**
     * Mapper任务功能就是将手机号按行进行切换，封装到对象中。
     * 这里做个复习，将map的输入value在读取的时候，直接封装成了FlowBean，练习一下
     * InputFormat的使用。
     */
    private static class PhoneMapper extends Mapper<Text, FlowBean, Text, FlowBean> {

        @Override
        protected void map(Text key, FlowBean value, Context context) throws IOException, InterruptedException {

            //将一行记录封装成FlowBean的处理在InputFormat阶段完成了。
            context.write(key, value);
        }
    }

    /**
     * reducer阶段做的任务就是将数据写出
     */
    private static class PhoneReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

            for (FlowBean value : values) {
                context.write(key, value);
            }
        }
    }
}
