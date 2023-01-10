package cn.hdfs.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;

/**
 * hdfs copy问题
 *
 * @author: mahao
 * @date: 2021/4/28
 */
public class HdfsCopy {

    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();

        FileSystem fs = FileSystem.get(configuration);

        boolean rename = fs.rename(new Path("/tmp/1.aa"), new Path("/tmp/2.aa"));

        System.out.println("执行状态 ： " + rename);
        fs.close();
    }
}
