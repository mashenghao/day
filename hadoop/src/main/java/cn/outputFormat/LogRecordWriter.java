package cn.outputFormat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * 真正的记录写出类，和InputFormat的实现机制是一样的。
 * 因为需要将数据写到两个不同的文件中，所以需要创建两个输出类。
 *
 * @author: mahao
 * @date: 2020/2/29
 */
public class LogRecordWriter extends RecordWriter<Text, NullWritable> {

    FSDataOutputStream out1 = null;
    FSDataOutputStream out2 = null;

    public LogRecordWriter(TaskAttemptContext job) {
        //1.获取文件系统。
        FileSystem fs;

        try {
            fs = FileSystem.get(job.getConfiguration());

            //2.创建两个路径
            Path p1 = new Path("D:\\tmp\\outputformat\\out\\f1.log");
            Path p2 = new Path("D:\\tmp\\outputformat\\out\\f2.log");

            //3.创建输入流
            out1 = fs.create(p1);
            out2 = fs.create(p2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        //reduce函数通过context引用调用，会循环写出数据。
        //判断key中是否包含关键字，用不同的out处理。
        if (key.toString().contains("atguigu")) {
            out1.write(key.getBytes());
        } else {
            out2.write(key.getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        // 关闭资源
        IOUtils.closeStream(out1);
        IOUtils.closeStream(out2);

    }
}
