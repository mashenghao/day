package basic.day19_IO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import org.junit.Test;

/**
 * 字符编码的练习
 *
 * @author mahao
 * @date: 2019年3月11日 下午1:16:28
 */
public class EncodeDemo {

	/**
	 * gbk编码是汉字编码格式，一个汉字占两个字节，字母还是一个字节<br>
	 * utf编码是新的编码表，一个汉字3个字节表示；
	 */
	@Test
	public void demo1() throws IOException {
		OutputStreamWriter out1 = new OutputStreamWriter(//
				new FileOutputStream("upload/code-utf.txt"), "UTF-8");// 3个字节
		OutputStreamWriter out2 = new OutputStreamWriter(//
				new FileOutputStream("upload/code-gbk.txt"), "GBK");// 2个字节
		out1.write("你好");
		System.out.println(Arrays.toString("你好".getBytes("UTF-8")));// 编码表[-28, -67, -96, -27, -91, -67]
		out2.write("你好");
		System.out.println(Arrays.toString("你好".getBytes("GBK")));// [-60, -29, -70, -61]
		out1.close();
		out2.close();
	}

	// 读取乱码原因
	@Test
	public void demo2() throws IOException {
		// 两个文件，code-utf.txt和code-gbk.txt
		InputStreamReader in1 = new InputStreamReader(//
				new FileInputStream("upload/code-utf.txt"), "UTF-8");
		InputStreamReader in2 = new InputStreamReader(//
				new FileInputStream("upload/code-gbk.txt"), "gbk");
		char[] buf = new char[10];
		// 如果采用读取文件的编码读取文件，咋不会乱码
		int len = in1.read(buf);
		String str = new String(buf, 0, len);
		System.out.println(str);
	}

	// 读取乱码原因---用gbk读取utf文件
	/**
	 * 原因： 读取的文件是utf-8编码，里面的汉字存储的时候，是将一个汉字通过查找utf码表，转换为3个<br>
	 * 字节存储，在硬盘上的数字表示是[-28, -67, -96, -27, -91, -67]，如果采用utf读取，<br>
	 * 在查找码表，则可以正确找到‘你好’； 指定的编码读取格式是GBK，gbk是两个字节存储一个汉字，<br>
	 * 则在读取的时候，去两个字节，去码表里面查找，恰好【-28，-67】，对应的汉字为‘浣’,以此类推<br>
	 * 也就是乱码了；
	 */
	@Test
	public void demo3() throws IOException {
		// 两个文件，code-utf.txt和code-gbk.txt
		InputStreamReader in = new InputStreamReader(//
				new FileInputStream("upload/code-utf.txt"), "GBK");
		char[] buf = new char[10];
		// 如果采用读取文件的编码读取文件，咋不会乱码
		int len = in.read(buf);
		String str = new String(buf, 0, len);
		System.out.println(str);// 浣犲ソ
	}

	// 读取乱码原因---用utf读取GBK文件
	/**
	 * gbk格式存储文件是将一个汉字通过查找gbk码表，将一个汉字在文件中用两个字节表示<br>
	 * 在文件中的表示是[-60, -29, -70, -61],读取的时候，是utf编码，是去3个字节去<br>
	 * 查找码表[-60, -29, -70]在码表中没有找到，则返回了一个�，只剩一个-61，<br>
	 * 找不到任然返回时？ 号； 所有gbk文件用utf读取，可能会有？？ ，utf文件用gbk读取，数据可能会错误
	 */
	@Test
	public void demo4() throws IOException {
		// 两个文件，code-utf.txt和code-gbk.txt
		InputStreamReader in = new InputStreamReader(//
				new FileInputStream("upload/code-gbk.txt"), "UTF-8");
		char[] buf = new char[10];
		// 如果采用读取文件的编码读取文件，咋不会乱码
		int len = in.read(buf);
		String str = new String(buf, 0, len);
		System.out.println(str);// ��
	}

	// encode 编码
	@Test
	public void demo5() throws IOException {
		// 项目默认的是UTF-8
		System.out.println(Arrays.toString("你好".getBytes()));
		// 可以指定编码
		System.out.println(Arrays.toString("你好".getBytes("GBK")));
		System.out.println(Arrays.toString("你好".getBytes("ASCII")));

		// 练习---编码正确，解码错误，对解码后的文件在编码会源文件，在采用正确编码解码
		byte[] by = "你好".getBytes();
		String str = new String(by, "ASCII");
		System.out.println("gbk解码utf字节数组--" + str);// 乱码--浣犲ソ
		byte[] by2 = str.getBytes("ASCII");
		String str2 = new String(by, "UTF-8");
		System.out.println("编码后在解码为--" + str2);// 正确--你好

	}

	//解码错误后，在重新编码，在用正确的格式解码
	@Test
	public void demo6() throws IOException {
		// 不能进行两次的中文编码
		byte[] by = "你好".getBytes("UTF-8");
		String str = new String(by, "GBK");
		System.out.println(Arrays.toString(by) + "解码字节数组--" + str);// 乱码--
		byte[] by2 = str.getBytes("GBK");// 重新编码
		String str2 = new String(by2, "UTF-8");
		System.out.println(Arrays.toString(by2) + "编码后在解码为--" + str2);// 错误
		// 错误原因，是utf编码,因为是utf重新编码是，把数字表示的源码，当做数字，又进行了一次编码；

		// 练习2 不能进行两次的中文编码
		byte[] by3 = "你好".getBytes("GBK");
		String str3 = new String(by3, "UTF-8");
		System.out.println(Arrays.toString(by3) + "utf解码gbk字节数组--" + str3);// 乱码--
		byte[] by4 = str3.getBytes("UTF-8");// 重新编码
		String str4 = new String(by4, "GBK");
		System.out.println(Arrays.toString(by4) + "utf编码后在解码为--" + str4);// 错误
		// 错误原因，是utf编码,因为是utf重新编码是，把数字表示的源码，当做数字，又进行了一次编码；
	}
}
