package cn.sort.groupsort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * 通过统计一个订单中最贵的商品来练习分组排序。
 * 思路：
 * 1.要的是显示每个订单号，和最贵商品的id，可以自定义排序规则，
 * 先按照订单id顺序排序，然后在按照上商品价格逆序排序。
 * <p>
 * 2. 在reduce阶段，只需要取出来第一个数据即可了。
 * <p>
 * 3.如果优化的话，可以在map阶段先进行reduce操作，取出来切片中的最大值，
 * 在将最大值传送到reduce阶段进行各个map的结果合并。
 *
 * @author: mahao
 * @date: 2020/2/27
 */
public class OrderDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[]{"D:/tmp/sort/groupsort/", "D:/tmp/sort/groupsort/out1"};

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(OrderDriver.class);
        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);

        job.setMapOutputKeyClass(Order.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Order.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //指定辅助排序。
        job.setGroupingComparatorClass(OrderGroupComparator.class);

        boolean b = job.waitForCompletion(true);
        System.out.println(b);
    }

    //负责将订单数据封装成对象，然后写出，在shuffle机制下，将会排序。
    private static class OrderMapper extends Mapper<LongWritable, Text, Order, NullWritable> {

        Order k = new Order();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String s = value.toString();
            String[] s1 = s.split("\t");
            k.setOid(Integer.parseInt(s1[0]));
            k.setPid(s1[1]);
            k.setPrice(Double.parseDouble(s1[2]));
            context.write(k, NullWritable.get());
        }
    }

    //取出排序好的数据，只取第一个数据，写到文件。
    private static class OrderReducer extends Reducer<Order, NullWritable, Order, NullWritable> {
        @Override
        protected void reduce(Order key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

            context.write(key, values.iterator().next());

            Iterator<NullWritable> it = values.iterator();
            while (it.hasNext()) {
                it.next();
                System.out.println(key);

            }
        }
    }
}
