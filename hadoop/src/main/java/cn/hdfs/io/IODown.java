package cn.hdfs.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 使用hdfs完成文件下载，fs.
 *
 * @author: mahao
 * @date: 2020/2/6
 */
public class IODown {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://10.100.2.90:8020"), configuration, "test");

        //1. 获取输入流
        FSDataInputStream fis = fs.open(new Path("/user/hive/warehouse/bakup/hdfs_insured_accresult2"));

        //2. 获取输出流
        FileOutputStream fos = new FileOutputStream("d://hdfs_insured_accresult2");

        //3. 流的拷贝
        IOUtils.copyBytes(fis, fos, 1024);

        fis.close();
        fos.close();
        fs.close();

    }
}
