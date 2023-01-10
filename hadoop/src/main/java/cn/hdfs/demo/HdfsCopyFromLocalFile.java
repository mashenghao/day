package cn.hdfs.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * hdfs测试文件上传，参数设置的优先级：
 * <p>
 * （1）客户端代码中设置的值 >
 * （2）ClassPath下的用户自定义配置文件 >
 * （3）然后是服务器的默认配置
 *
 * @author: mahao
 * @date: 2020/2/6
 */
public class HdfsCopyFromLocalFile {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        //1. 获取文件系统
        Configuration conf = new Configuration();
        //conf.set("dfs.replication", "2");//参数设定备份数
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "mahao");

        //2. 上传文件
        fs.copyFromLocalFile(new Path("D:\\Java\\ideas\\hadoopDemo\\target\\hadoopDemo-1.0-SNAPSHOT.jar"), new Path("/user/mahao/hadoop"));

        //3. 关闭资源
        fs.close();
        System.out.println("over!");
    }
}
