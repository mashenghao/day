package cn.example.friend;

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
 * 寻找共同好友。
 * 是寻找 A∩B的交集：
 * <p>
 * 如果是小数据，则可以通过A的集合和B的集合进行二次循环即可。但是如果是庞大的数据计算，
 * 要求A与一个系统中所有人的共同好友，如何计算。
 *
 * 思路：
 * A: B,C,D,E
 * B: A,C
 * C: A,B,D
 *
 * 1.我们可以先求出，同时有A好友的成员
 *   可以map组合成<友,人>的关系，在reduce合并，找出 友：人,人,人 的格式。
 * 2. 查看  友：人1,人2,人3 的格式，我们知道 人1∩人2=友，所以人1∩人2结果之一是友，
 *    可以map阶段，组成 人1∩人2 = 友 的关系，在reduce阶段，对 人1∩人2 进行汇总。注意 人1∩人2 和 人2∩人1 不是一个。
 *
 *
 *
 * @author: mahao
 * @date: 2020/3/9
 */
public class FriendDriver1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
// 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[]{"D:\\tmp\\example\\friend\\", "D:\\tmp\\example\\friend\\out1"};

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(FriendDriver1.class);

        job.setMapperClass(FriendMapper1.class);
        job.setReducerClass(FriendReducer1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

    //这一阶段是，将人与友组成 <友，人>的关系。
    private static class FriendMapper1 extends Mapper<LongWritable, Text,Text,Text> {

        Text k = new Text();
        Text v = new Text();

        @Override//A: B,C,D
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (line.length()==0){
                return;
            }
            String[] split = line.split(":");
            String person  = split[0];
            String[] friends = split[1].split(",");
            v.set(person);
            /*
            这一步写出的格式是：在reducer可以计算出同时为某一好友的人。
            BA，CA，DA
             */
            for (String friend : friends) {
                k.set(friend);
                context.write(k,v);
            }
        }
    }

    //这一步是找出有某一好友的所有人。
    private static class FriendReducer1 extends Reducer<Text,Text,Text,Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuffer sb = new StringBuffer();
            for (Text value : values) {
                sb.append(value.toString()).append(",");
            }
            context.write(key,new Text(sb.toString()));
        }
    }
}
