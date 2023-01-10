package cn.hdfs.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 按块读取文件内容：
 *
 * @author: mahao
 * @date: 2020/2/6
 */
public class ReadFileSeek1 {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), configuration, "mahao");

        FSDataInputStream fis = fs.open(new Path("/hadoop-2.7.2.tar.gz"));

        // 2 创建输出流
        FileOutputStream fos = new FileOutputStream(new File("d:/hadoop-2.7.2.tar.gz.part1"));

        //流的拷贝，拷贝第一块的block
        byte[] buf = new byte[1024];//1KB
        for (int i = 0; i < 1024 * 128; i++) {
            fis.read(buf);
            fos.write(buf);
        }

        fis.close();
        fos.close();
        fs.close();

    }

    @Test
    public void readSeek2() throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), configuration, "mahao");

        FSDataInputStream fis = fs.open(new Path("/hadoop-2.7.2.tar.gz"));

        fis.seek(1024*1024*128);//定位位置
        // 4 创建输出流
        FileOutputStream fos = new FileOutputStream(new File("d:/hadoop-2.7.2.tar.gz.part2"));

        // 5 流的对拷
        IOUtils.copyBytes(fis, fos, configuration);

        // 6 关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

    }
}

