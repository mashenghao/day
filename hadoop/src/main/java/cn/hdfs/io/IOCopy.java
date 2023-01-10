package cn.hdfs.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * 使用hdfs的IO流完成fs.copyFromLocalFile()。
 * 2.剖析hdfs文件写入流程。
 *
 * @author: mahao
 * @date: 2020/2/6
 */
public class IOCopy {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), configuration, "mahao");

        //1.创建输入流
        FileInputStream fis = new FileInputStream("d://11.txt");

        //2.在hdfs上打开输出流
        FSDataOutputStream fos = fs.create(new Path("/user/mahao/input/11.txt"));

        //3.流的拷贝
        IOUtils.copyBytes(fis, fos, configuration);

        fis.close();
        fos.close();
        fs.close();
    }
}
