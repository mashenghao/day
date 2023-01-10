package cn.inputformat.myInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


import java.io.IOException;

/**
 * 这个是真正读取文件内容的类。
 *
 * @author: mahao
 * @date: 2020/2/20
 */
public class WholeRecordReader extends RecordReader<Text, BytesWritable> {

    private Configuration configuration;
    private FileSplit split;
    private Text k = new Text();
    private BytesWritable v = new BytesWritable();
    private boolean isRead = true; //是否允许读取，是否第一次访问。

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        this.split = (FileSplit) split;
        this.configuration = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (isRead) {
            byte[] buf = new byte[(int) split.getLength()];
            FileSystem fs;
            FSDataInputStream fis = null;
            try {
                fs = split.getPath().getFileSystem(configuration);
                fis = fs.open(split.getPath());

                IOUtils.readFully(fis, buf, 0, buf.length);

                v.set(buf, 0, buf.length);
                k.set(split.getPath().toString());
            } finally {
                IOUtils.closeStream(fis);
            }
            isRead = false;
            return true;

        }
        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return k;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return v;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
