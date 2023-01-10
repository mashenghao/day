package cn.example.topN;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author: mahao
 * @date: 2020/3/9
 */
public class FlowBean2 implements WritableComparable {


    private int upFlow;
    private int downFlow;
    private int sumFlow;

    //反序列化时，需要反射调用无参构造，生成对象
    public FlowBean2() {
        super();
    }

    //序列化
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(upFlow);
        out.writeInt(downFlow);
        out.writeInt(sumFlow);
    }

    //反序列化
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

    //按照sum降序排列
    @Override
    public int compareTo(Object o) {
        FlowBean2 f = (FlowBean2) o;
        return f.sumFlow - this.sumFlow;
    }
}
