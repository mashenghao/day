package cn.hdfs.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * 测试一些常用的api
 *
 * @author: mahao
 * @date: 2020/2/6
 */
public class HdfsDemo2 {

    FileSystem fs = null;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), configuration, "mahao");
    }

    //测试文件的删除
    @Test
    public void testDelete() throws IOException {

        //删除文件或者文件夹，recursive是是否递归删除。
        boolean b = fs.delete(new Path("/user/mahao/11.txt"), true);

        System.out.println(b);
    }


    //测试重命名文件或者文件夹
    @Test
    public void testRename() throws IOException {

        boolean b = fs.rename(new Path("/user/ls2"), new Path("/user/ls"));

        System.out.println(b);
    }

    //测试查看文件的详情，包括文件名称权限，长度，块信息
    @Test
    public void testListFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(new Path("/"), true);
        while (iterator.hasNext()) {
            LocatedFileStatus status = iterator.next();
            System.out.println("文件名称： " + status.getPath().getName());
            System.out.println("长度： " + status.getLen());
            System.out.println("权限： " + status.getPermission());
            System.out.println("分组： " + status.getGroup());

            //获取存储的块信息
            BlockLocation[] locations = status.getBlockLocations();
            for (BlockLocation location : locations) {
                String[] hosts = location.getHosts();
                System.out.println("存储的主机节点： " + Arrays.toString(hosts));
            }
        }
    }


    /**
     * test file or directory
     */
    @Test
    public void testListStatus() throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/user"));
        for (FileStatus fileStatus : fileStatuses) {
            boolean directory = fileStatus.isDirectory();
            if (directory) {
                System.out.println("d: " + fileStatus.getPath().getName());
            } else {
                System.out.println("f: " + fileStatus.getPath().getName());
            }
        }
    }


    @After
    public void close() throws IOException {
        if (fs != null) {
            fs.close();
        }
    }

}


