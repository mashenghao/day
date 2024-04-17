package zip;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Bzip2 {

    public static String compress(String input) throws IOException {
        // 将字符串转换为字节数组
        byte[] data = input.getBytes("UTF-8");

        // 创建一个字节数组输出流来存储压缩后的数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 创建Bzip2压缩输出流
        try (BZip2CompressorOutputStream bzip2OutputStream = new BZip2CompressorOutputStream(outputStream)) {
            // 将数据压缩并写入输出流
            bzip2OutputStream.write(data);
        }

        // 使用Base64编码将压缩后的数据转换为字符串并返回
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public static String decompress(String compressedInput) throws IOException {

        // 使用Base64解码压缩后的字符串
        byte[] compressedData = Base64.getDecoder().decode(compressedInput);

        // 创建一个字节数组输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);

        // 创建Bzip2解压缩输入流
        try (BZip2CompressorInputStream bzip2InputStream = new BZip2CompressorInputStream(inputStream)) {
            // 从解压缩流中读取数据
            byte[] decompressedBytes = IOUtils.toByteArray(bzip2InputStream);

            // 将解压后的数据转换为字符串并返回
            return new String(decompressedBytes, "UTF-8");
        }
    }
}
