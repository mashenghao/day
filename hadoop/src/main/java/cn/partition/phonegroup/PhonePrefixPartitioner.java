package cn.partition.phonegroup;

import cn.mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
import org.junit.Assert;
import org.junit.Test;

/**
 * 根据手机号前缀划分分区
 *
 * @author: mahao
 * @date: 2020/2/26
 */
public class PhonePrefixPartitioner extends Partitioner<Text, FlowBean> {

    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        String pre = text.toString().substring(0, 3);
        int result = 4;
        switch (pre) {
            case "136":
                result = 0;
                break;
            case "137":
                result = 1;
                break;
            case "138":
                result = 2;
                break;
            case "139":
                result = 3;
                break;
            default:
                result = 4;
        }
        return result;
    }

    @Test
    public void subString() {
        Assert.assertEquals("139", "139132".substring(0, 3));
    }
}
