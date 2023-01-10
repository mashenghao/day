package cn.join;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 练习join的使用，案例是通过订单表和商品表，显示订单项的详情。
 * 方式就是通过join。
 *
 * @author: mahao
 * @date: 2020/2/29
 */
public class ItemDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 0 根据自己电脑路径重新配置
        args = new String[]{"D:\\tmp\\join", "D:\\tmp\\join\\out1"};

        // 1 获取配置信息，或者job对象实例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 2 指定本程序的jar包所在的本地路径
        job.setJarByClass(ItemDriver.class);

        // 3 指定本业务job要使用的Mapper/Reducer业务类
        job.setMapperClass(ItemMapper.class);
        job.setReducerClass(ItemReducer.class);

        // 4 指定Mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Item.class);

        // 5 指定最终输出的数据的kv类型
        job.setOutputKeyClass(Item.class);
        job.setOutputValueClass(NullWritable.class);

        // 6 指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        // 7 将job中配置的相关参数，以及job所用的java类所在的jar包， 提交给yarn去运行
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }

    //负责将从不同表中读取的记录打上标签，并进行封装。
    private static class ItemMapper extends Mapper<LongWritable, Text, Text, Item> {

        Text k = new Text();
        Item v = new Item();
        String filename;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit split = (FileSplit) context.getInputSplit();
            filename = split.getPath().getName();

        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String toString = value.toString();
            String[] split = toString.split("\t");
            if (filename.contains("order")) {
                k.set(split[1]);
                v.setOid(split[0]);
                v.setPid(split[1]);
                v.setAmount(Integer.parseInt(split[2]));
                v.setFlag("order");

            } else {
                k.set(split[0]);
                v.setPid(split[0]);
                v.setPname(split[1]);
                v.setFlag("product");
            }
            context.write(k, v);
        }
    }


    //reducer阶段，负责对已经打好了标签的数据，进行挑选组合。
    private static class ItemReducer extends Reducer<Text, Item, Text, Item> {

        Text k = new Text();

        @Override
        protected void reduce(Text key, Iterable<Item> values, Context context) throws IOException, InterruptedException {
            List<Item> orders = new ArrayList<>();//存储key对应的订单项目
            Item product = new Item(); //存储的商品。

            for (Item value : values) {
                // 拷贝传递过来的每条订单数据到集合中
                Item orderBean = new Item();
                try {
                    BeanUtils.copyProperties(orderBean, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (value.getFlag().equals("order")) {
                    orders.add(orderBean);
                } else {
                    product = orderBean;
                }
            }

            for (Item item : orders) {
                item.setPname(product.getPname());
                k.set(item.getOid());
                System.out.println(k + "......................" + item);
                context.write(k, item);

            }
        }
    }

}
