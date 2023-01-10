package cn.mapreduce.flowsum;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: mahao
 * @date: 2020/2/14
 */
public class FlowSumMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    Text k = new Text();
    FlowBean v = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] values = value.toString().split("\t");

        //输出数据key是手机号
        k.set(values[1]);
        //输出数据value是upflow和downflow
        int len = values.length;
        int down = Integer.parseInt(values[len - 2]);
        int up = Integer.parseInt(values[len - 3]);
        v.setDownFlow(down);
        v.setUpFlow(up);

        //写出数据
        context.write(k, v);
    }
}
