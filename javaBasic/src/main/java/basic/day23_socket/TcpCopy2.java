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
 * 类名：并发上传文件
 * 
 * @author mahao
 * @date 2018年6月8日 Description:
 */
public class TcpCopy2 {

	@Test
	public void xun() throws Exception {
		for (int i = 0; i < 5; i++) {
			Thread.sleep(5000);
			new TcpCopy2().client();
		}
		System.out.println("1"+System.currentTimeMillis());
	}

	@Test
	public void xun2() throws Exception {
		for (int i = 0; i < 5; i++) {
			Thread.sleep(3000);
			new TcpCopy2().client();
		}
		System.out.println("2"+System.currentTimeMillis());
	}
	
	@Test
	public void xun3() throws Exception {
		for (int i = 0; i < 5; i++) {
			//Thread.sleep(5000);
			new TcpCopy2().client();
		}
		System.out.println("3"+System.currentTimeMillis());
	}
	
	//@Test
	public void client() throws Exception {
		Socket s = new Socket("192.168.157.1", 8000);
		BufferedInputStream fileIn = new BufferedInputStream(
				new FileInputStream("2.avi"));
		BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
		byte[] b = new byte[1024];
		int len;
		while ((len = fileIn.read(b)) != -1) {
			out.write(b, 0, len);
			out.flush();
		}
		s.shutdownOutput();
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(Thread.currentThread().getName() + in.readLine());
		out.close();
	}

	//@Test
	public void server() throws Exception {
		int a = 0;
		ServerSocket ss = new ServerSocket(5000);
		System.out.println("服务器开启------");
		while (true) {
			Socket s = ss.accept();
			new Thread(new UpThread(s)).start();
			System.out.println(Thread.currentThread().getName());
		}

	}
}

class UpThread implements Runnable {
	private Socket s;

	public UpThread(Socket socket) {
		this.s = socket;
	}

	public void run() {
		
		int a = 1;
		BufferedInputStream in;
		try {
			String si;
			in = new BufferedInputStream(s.getInputStream());
			if ("172.21.3.28".equals(s.getInetAddress().getHostAddress()))
				si=".avi";
			else
				si=".tar";
			File file = new File("C:/Users/Administrator/Desktop/upload/" + a +si);
			while(file.exists()) 
				file = new File("C:/Users/Administrator/Desktop/upload/" + (a++) + si);
			
			BufferedOutputStream br = new BufferedOutputStream(
					new FileOutputStream(file));
			byte[] b = new byte[1024];
			int len;
			while ((len = in.read(b)) != -1) {
				//System.out.println(Thread.currentThread().getName());
				br.write(b, 0, len);
				br.flush();
			}
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println("传输完成"+s.getInetAddress().getHostAddress()+
					"--------------"+s.getPort()+Thread.currentThread().getName());
			Thread.sleep(5000);
			System.out.println("传输完成"+s.getInetAddress().getHostAddress()+
					"--------------"+s.getPort()+Thread.currentThread().getName());
			in.close();
			s.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
