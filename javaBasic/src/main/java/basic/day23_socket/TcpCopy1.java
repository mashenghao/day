package basic.day23_socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;
/**
 * 
 * 类名：单线程上传文件
 * 
 * @author mahao
 * @date 2018年6月8日
 * Description:
 */
public class TcpCopy1 {

	@Test
	public void client() throws Exception {
		Socket s = new Socket("172.21.3.28", 5000);
		BufferedInputStream fileIn = new BufferedInputStream(
				new FileInputStream("C:/Users/Administrator/Desktop/01.avi"));
		BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
		byte[] b = new byte[1024];
		int len;
		while ((len = fileIn.read(b)) != -1) {
			System.out.println(len);
			out.write(b, 0, len);
			out.flush();
		}
		s.shutdownOutput();
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(in.readLine());
		out.close();
	}

	@Test
	public void server() throws Exception {
		int a = 0;
		ServerSocket ss = new ServerSocket(5000);
		while (true) {
			Socket s = ss.accept();
			BufferedInputStream in = new BufferedInputStream(s.getInputStream());
			BufferedOutputStream br = new BufferedOutputStream(
					new FileOutputStream(new File("C:/Users/Administrator/Desktop/upload/" + a + "01.avi")));
			a++;
			byte[] b = new byte[1024];
			int len;
			while ((len = in.read(b)) != -1) {
				System.out.println(len);
				br.write(b, 0, len);
				br.flush();
			}
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println("传输完成");
			s.close();
			out.close();
			br.close();
		}

	}
}
