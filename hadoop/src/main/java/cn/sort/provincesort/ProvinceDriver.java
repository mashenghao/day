package cn.sort.provincesort;

import cn.mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 根据分区的那个案例，实现同一个省的手机号，输出到一个文件，
 * 手机号内部实现降序排序。
 * <p>
 * 思路：
 * 1. 需要重新封装对象，作为key，比较的内容是先按照号码前三位，一直后比较流量。
 * 2. 输出到了多个文件，所以需要创建Partitioner。
 * 3. 需要指定numReduceTask数。
 *
 * @author: mahao
 * @date: 2020/2/27
 */
public class ProvinceDriver {

    public static void main(String[] args) {

    }

    private static class ProvinceMapper extends Mapper<LongWritable, Text, FlowBean2, NullWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        }
    }

    /**
     * reducer阶段做的任务就是将数据写出
     */
    private static class ProvinceReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

            for (FlowBean value : values) {
                context.write(key, value);
            }
        }
    }
}
