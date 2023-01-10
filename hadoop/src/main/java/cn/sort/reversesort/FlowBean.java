package cn.sort.reversesort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 需要实现WritbleCompareable接口
 *
 * @author: mahao
 * @date: 2020/2/26
 */
public class FlowBean implements WritableComparable<FlowBean> {

    private int upFlow;
    private int downFlow;
    private int sumFlow;

    public FlowBean() {
    }

    //按照sumflow逆序输出。
    @Override
    public int compareTo(FlowBean o) {

        return sumFlow > o.sumFlow ? -1 : sumFlow == o.sumFlow ? 0 : 1;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(upFlow);
        out.writeInt(downFlow);
        out.writeInt(sumFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow = in.readInt();
        downFlow = in.readInt();
        sumFlow = in.readInt();
    }

    //编写toString接口。方便后续打印到文本
    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    public int getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(int upFlow) {
        this.upFlow = upFlow;
    }

    public int getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(int downFlow) {
        this.downFlow = downFlow;
    }

    public int getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(int sumFlow) {
        this.sumFlow = sumFlow;
    }
}
