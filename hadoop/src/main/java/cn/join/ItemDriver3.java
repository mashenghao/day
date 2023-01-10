package cn.join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * ItemDriver实现在map端的数据join，预先读取到数据。
 *
 * @author: mahao
 * @date: 2020/2/29
 */
public class ItemDriver3 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        // 0 根据自己电脑路径重新配置
        args = new String[]{"D:\\tmp\\join", "D:\\tmp\\join\\out1"};

        // 1 获取配置信息，或者job对象实例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 2 指定本程序的jar包所在的本地路径
        job.setJarByClass(ItemDriver3.class);

        // 3 指定本业务job要使用的Mapper/Reducer业务类
        job.setMapperClass(ItemMapper3.class);
        job.setReducerClass(ItemReducer3.class);

        // 4 指定Mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Item.class);

        // 5 指定最终输出的数据的kv类型
        job.setOutputKeyClass(Item.class);
        job.setOutputValueClass(NullWritable.class);

        // 6 指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //加载到缓存文件
        job.addCacheFile(new URI("file:///d:/tmp/pd.txt"));

        // 7 将job中配置的相关参数，以及job所用的java类所在的jar包， 提交给yarn去运行
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }

    //负责将从不同表中读取的记录打上标签，并进行封装。
    private static class ItemMapper3 extends Mapper<LongWritable, Text, Text, Item> {

        Text k = new Text();
        Item v = new Item();
        Map<String, String> map = new HashMap<>();

        //在map方法之前，将商品表信息预先加载到内存中。
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            URI[] cacheFile = context.getCacheFiles();
            String path = cacheFile[0].getPath();
            FileSystem fs = FileSystem.get(context.getConfiguration());
            FSDataInputStream in = fs.open(new Path(path));
            BufferedReader fis = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = fis.readLine()) != null) {
                String[] split = line.split("\t");
                map.put(split[0], split[1]);
            }
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String toString = value.toString();
            String[] split = toString.split("\t");
            k.set(split[1]);
            v.setOid(split[0]);
            v.setPid(split[1]);
            v.setAmount(Integer.parseInt(split[2]));
            v.setPname(map.get(split[1]));
            context.getCounter("aa", "a1").increment(1);
            context.getCounter(MyEnum.A1).increment(1);
            context.write(k, v);
        }

        enum MyEnum {
            A1, A2;
        }
    }


    //reducer阶段，负责对已经打好了标签的数据，进行挑选组合。
    private static class ItemReducer3 extends Reducer<Text, Item, Text, Item> {

        @Override
        protected void reduce(Text key, Iterable<Item> values, Context context) throws IOException, InterruptedException {
            for (Item value : values) {
                context.write(key, value);
            }
        }
    }

}