package cn.hdfs.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 获取hdfs客户端FileSystem。
 *
 * @author: mahao
 * @date: 2020/2/6
 */
public class HdfsClient {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        //1. 获取hdfs的文件系统
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://hadoop01:9000");
        FileSystem fs = FileSystem.get(configuration);
        FileSystem fs2 = FileSystem.get(new URI("hdfs://hadoop01:9000"), configuration, "mahao");
        //2. 操作hdfs文件系统
        /*
            在操作的时候，会使用默认的电脑用户名，如果想指定用户访问hdfs系统，则通过
            1. 指定vm参数-DHADOOP_USER_NAME=mahao
            2. 创建连接时，指定用户名。
         */
        boolean b = fs2.mkdirs(new Path("/user/ls4"));
        System.out.println(b);

        //3. 关闭连接
        fs.close();
        System.out.println("over");
    }

}
