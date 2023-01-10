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
import org.junit.Test;

import java.io.IOException;

/**
 * 这一步是找好友关系的第二步，通过第一步创建的那种
 * f: p1,p2,p3 关系，
 * 我们知道p1和p2一定有共同好友f，依次类推，我们就可以找出好友关系了。
 *
 * @author: mahao
 * @date: 2020/3/9
 */
public class FriendDriver2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
// 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[]{"D:\\tmp\\example\\friend\\out1\\part-r-00000", "D:\\tmp\\example\\friend\\out2"};

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(FriendDriver2.class);

        job.setMapperClass(FriendMapper2.class);
        job.setReducerClass(FriendReducer2.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }


    //map阶段，完成人人的组合： f: p1,p2,p3
    private static class FriendMapper2 extends Mapper<LongWritable, Text, Text, Text> {

        Text k = new Text();
        Text v = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split("\t");
            v.set(split[0]);
            String[] persons = split[1].split(",");
            //将他们组成A∩B=F的关系
            for (int i = 0; i < persons.length-1; i++) {
                for (int j = i+1; j < persons.length; j++) {//j=1因为A∩A是不成立的。
                    String str = persons[i] + "∩" + persons[j];
                    k.set(str);
                    context.write(k, v);
                }
            }

        }
    }


    private static class FriendReducer2 extends Reducer<Text, Text, Text, Text> {

        Text v = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuffer sb = new StringBuffer();
            for (Text value : values) {
                sb.append(value.toString()).append(",");
            }

            v.set(sb.toString());
            context.write(key, v);
        }
    }

    @Test
    public void test1(){
        String[] s = "A,B,C,".split(",");
        System.out.println(s.length);
    }
}
