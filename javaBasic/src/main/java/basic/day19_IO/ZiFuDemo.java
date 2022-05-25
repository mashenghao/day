package basic.day19_IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

/**
 * 字符流练习；
 *
 * @author mahao
 * @date: 2019年3月7日 下午7:39:54
 */
public class ZiFuDemo {

	// FileWriter FileReader
	@Test
	public void demo1() throws IOException {
		// 默认路径是项目路径,/javaBasic/
		FileWriter writer = new FileWriter("upload/demo1.txt");
		writer.write("asdfg");
		writer.flush();
		writer.write("asdfg");
		writer.flush();
	}

	// 文件复制
	@Test
	public void demo2() throws IOException {
		// 默认路径是项目路径,/javaBasic/
		FileReader reader = new FileReader("upload/myServer_copy.java");
		FileWriter writer = new FileWriter("upload/myServer_copy3.java");
		long start = System.currentTimeMillis();
		run2 (reader, writer);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		reader.close();
		writer.close();
	}

	// 单个字符复制
	private void run(FileReader reader, FileWriter writer) throws IOException {
		int ch = -1;
		while ((ch = reader.read()) != -1) {
			writer.write(ch);
		}
		writer.flush();
	}

	// 字符数组复制
	private void run2(FileReader reader, FileWriter writer) throws IOException {
		char[] ch = new char[1024];
		while (reader.read(ch) != -1) {
			writer.write(ch, 0, ch.length);
		}
		writer.flush();
	}

	// 带缓冲区的复制
	private void run3(FileReader reader, FileWriter writer) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		BufferedWriter bw = new BufferedWriter(writer);
		String line = null;
		while((line = br.readLine())!=null) {
			bw.write(line);
			bw.newLine();
		}
		bw.close();
		br.close();
	}

	// 个换行为\r\n,是两个字符
	@Test
	public void demo3() throws IOException {
		FileReader reader = new FileReader("upload/demo1.txt");
		int ch = -1;// 一个换行为\r\n,是两个字符
		while ((ch = reader.read()) != -1) {
			System.out.println((char) ch);
		}
		char[] chs = new char[5];
		while (reader.read(chs) != -1) {
			System.out.println(chs);
		}
	}

	// 自定义bufferReader，读取一行数据
	@Test
	public void demo4() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/basic.day19_IO/ZiFuDemo.java"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("upload/demo1.txt"));
		bw.newLine();
		String str = null;
		while ((str = br.readLine()) != null) {
			bw.write(str);
			bw.newLine();
		}
		br.close();
		bw.close();
		// 自定义增强类
		MyBufferedReader mbr = new MyBufferedReader(new FileReader("upload/demo1.txt"));
		String str1 = null;
		while ((str1 = mbr.readLine()) != null) {
			System.out.println(mbr.getNum()+str1);
		}
	}

	// 自定义增强读取类
	private class MyBufferedReader {
		private Reader reader;
		private int num=0;
		public MyBufferedReader(Reader reader) {
			this.reader = reader;
		}

		public int getNum() {
			return num;
		}
		
		public String readLine() throws IOException {
			int ch = -1;
			StringBuilder sb = new StringBuilder();
			while ((ch = reader.read()) != -1) {
				if (ch == '\r')
					continue;
				else if (ch == '\n') {
					num++;
					return sb.toString();
				}
				else
					sb.append((char) ch);
			}
			return null;
		}
	}

}
