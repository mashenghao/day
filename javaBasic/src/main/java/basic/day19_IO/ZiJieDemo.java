package basic.day19_IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

/**
 * 字节数组练习
 *
 * @author mahao
 * @date: 2019年3月8日 下午7:17:59
 */
public class ZiJieDemo {

	@Test
	public void demo1() {
		String str = "一";
		byte[] bytes = str.getBytes();
		System.out.print(Arrays.toString(bytes));
	}

	@Test
	public void demo2() throws IOException {
		FileInputStream fin = new FileInputStream("upload/demo1.txt");
		FileOutputStream fos = new FileOutputStream("upload/demo2.txt");
		System.out.println("返回文件的大小" + fin.available());
		int ch = -1;
		while ((ch = fin.read()) != -1) {
			System.out.println((char) ch);
			fos.write(ch);
		}
		fin.close();
		fos.close();
	}

	// 拷贝图片
	@Test
	public void demo3() throws IOException {
		FileInputStream fin = new FileInputStream("upload/00.jpg");
		FileOutputStream fos = new FileOutputStream("upload/00_1.jpg");
		System.out.println("返回文件的大小" + fin.available());
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = fin.read(buf)) != -1) {
			fos.write(buf, 0, len);
		}
		fin.close();
		fos.close();
	}

	// 带缓冲区字节流
	@Test
	public void demo4() throws IOException {
		MyBufferedInputStream in = new MyBufferedInputStream(new FileInputStream("upload/08.jpg"));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("upload/09.jpg"));
		int ch = -1;
		while ((ch = in.myRead()) != -1) {
			out.write(ch);
		}
		//in.close();
		out.close();
	}

	// 键盘数据跑存到硬盘
	@Test
	public void demo5() throws IOException {
		BufferedInputStream in = new BufferedInputStream(System.in);
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("upload/demo2.txt"));
		int ch = -1;
		while ((ch = in.read()) != -1) {
			System.out.println(ch);
			out.write(ch);
		}
		in.close();
		out.close();
	}

	// 自定义字节缓冲流
	@Test
	public void demo6() throws IOException {
		MyBufferedInputStream in = new MyBufferedInputStream(new FileInputStream("upload/demo1.txt"));
		int ch = -1;
		while ((ch = in.myRead()) != -1) {
			System.out.println((char)ch);
		}
	}

	private class MyBufferedInputStream {

		private InputStream in;
		private byte[] buf = new byte[3];
		int len = 0;
		int count = 0;

		public MyBufferedInputStream(InputStream in) {
			this.in = in;
		}

		public int myRead() throws IOException {
			if (len == 0) {
				len = in.read(buf);
				if (len ==-1) 
					return -1;
				count=0;
			}
			len--;
			return buf[count++]&0xff;
		}
	}
}
