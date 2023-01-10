package cn.inputformat.myInputFormat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

 import java.io.IOException;

/**
 * 这个是自定义FileInputFormat，用来读取整个文件。
 *
 * @author: mahao
 * @date: 2020/2/20
 */
public class WholeInputFormat extends FileInputFormat<Text, BytesWritable> {

    //返回当前文件不支持切片，所以将getSpilets方法失效了，现在是一个文件作为一个切片。
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        //自定义实现RecordReader，实现读取整个文件的操作。
        WholeRecordReader reader = new WholeRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}
