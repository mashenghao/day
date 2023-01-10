package cn.mapreduce.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * @author: mahao
 * @date: 2020/2/14
 */
public class FlowSumReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    FlowBean v = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        // 计算合并
        int upSum = 0;
        int dowmSum = 0;
        int sum = 0;
        for (FlowBean value : values) {
            upSum += value.getUpFlow();
            dowmSum = value.getDownFlow();
        }
        sum = upSum + dowmSum;

        //封装并写出
        v.setUpFlow(upSum);
        v.setDownFlow(dowmSum);
        v.setSumFlow(sum);

        context.write(key, v);

    }
}
