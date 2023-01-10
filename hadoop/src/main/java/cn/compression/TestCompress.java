package cn.compression;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;

import java.io.*;

/**
 * @author: mahao
 * @date: 2020/3/1
 */
public class TestCompress {

    public static void main(String[] args) throws Exception {
        compress("D:\\tmp\\ETL\\web.log", "org.apache.hadoop.io.compress.DefaultCodec");
        // decompress("D:\\tmp\\ETL\\web.log.");
    }

    /**
     * 压缩文件
     *
     * @param file   待压缩文件全路径
     * @param method 压缩方法
     */
    private static void compress(String file, String method) throws Exception {
        //1.获取原数文件的输入流
        FileInputStream in = new FileInputStream(new File(file));
        Class<CompressionCodec> clazz = (Class<CompressionCodec>) Class.forName(method);
        CompressionCodec codec = clazz.newInstance();

        //2.获取输出流
        FileOutputStream fos = new FileOutputStream(new File(file + codec.getDefaultExtension()));

        //3. 使用压缩流，增强文件输出流
        CompressionOutputStream cos = codec.createOutputStream(fos);

        //4.流的拷贝
        IOUtils.copyBytes(in, cos, 1024);

        //5.关闭资源
        cos.close();
        fos.close();
        in.close();
    }

    /**
     * 解压缩
     *
     * @param file
     */
    private static void decompress(String file) throws Exception {
        //校验是否能解压缩
        CompressionCodecFactory codecFactory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = codecFactory.getCodec(new Path(file));
        if (codec == null) {
            System.out.println("不支持");
            return;
        }

        //1.获取压缩文件原始输入流
        FileInputStream fis = new FileInputStream(file);

        //2. 使用解压缩流包装
        CompressionInputStream cis = codec.createInputStream(fis);

        //3.输出文件
        FileOutputStream fos = new FileOutputStream(file + ".decoe");

        //4.流的拷贝
        IOUtils.copyBytes(cis, fos, 1024 * 1024 * 5, false);

        cis.close();
        fos.close();
    }
}
