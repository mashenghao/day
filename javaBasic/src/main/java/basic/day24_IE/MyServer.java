package basic.day24_IE;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 类名：自定义浏览器服务器
 * 
 * @author mahao
 * @date 2018年6月9日 Description:
 */
public class MyServer {

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(8000);
		System.out.println("开启成功，等待连接------");
		while (true) {
			Socket s = ss.accept();
			System.out.println(s.getInetAddress().getHostAddress());
			InputStream in = s.getInputStream();

			byte[] buf = new byte[1024 * 4];
			int len = in.read(buf);
			System.out.println(new String(buf, 0, len));

			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println("<font color='red' size='7'>客户端你好</font>");
			s.close();
		}
	}
}
