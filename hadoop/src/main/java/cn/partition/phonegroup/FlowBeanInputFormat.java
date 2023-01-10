package cn.partition.phonegroup;

import cn.mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;

/**
 * 读取数据，封装成FlowBean,实现思路可以参看KeyValueLineRecordReader
 * @author: mahao
 * @date: 2020/2/26
 */
public class FlowBeanInputFormat extends FileInputFormat<Text, FlowBean> {

    @Override
    public RecordReader<Text, FlowBean> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        FlowBeanRecordReader flowBeanRecordReader = new FlowBeanRecordReader(split, context);
        flowBeanRecordReader.initialize(split, context);
        return flowBeanRecordReader;
    }

    //读取数据，封装成FlowBean,实现思路可以参看KeyValueLineRecordReader
    private class FlowBeanRecordReader extends RecordReader<Text, FlowBean> {

        private LineRecordReader recordReader;
        private Text k = new Text();
        private FlowBean v = new FlowBean();

        public FlowBeanRecordReader(InputSplit split, TaskAttemptContext context) {
            recordReader = new LineRecordReader();
        }

        @Override
        public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
            recordReader.initialize(split, context);
        }

        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            boolean b = recordReader.nextKeyValue();
            if (b) {
                Text currentValue = recordReader.getCurrentValue();
                String line = currentValue.toString();
                String[] split = line.split("\t");
                k.set(split[0]);
                v.setUpFlow(1);
                v.setDownFlow(2);
                v.setSumFlow(3);
            }
            return b;
        }

        @Override
        public Text getCurrentKey() throws IOException, InterruptedException {
            return k;
        }

        @Override
        public FlowBean getCurrentValue() throws IOException, InterruptedException {
            return v;
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            return recordReader.getProgress();
        }

        @Override
        public void close() throws IOException {
            recordReader.close();
        }
    }
}
