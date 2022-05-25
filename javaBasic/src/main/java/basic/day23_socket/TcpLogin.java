package basic.day23_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

/**
 * 多用户登录，3次尝试判断 类名：
 * 
 * @author mahao
 * @date 2018年6月9日 Description:
 */
public class TcpLogin {

	@Test
	public void client() throws Exception {
		Socket s = new Socket("172.21.3.43", 5000);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);

		for (int i = 0; i < 3; i++) {
			String name = br.readLine();
			out.println(name);
			String info = in.readLine();
			System.out.println("info:  " + info);
			if (info.contains("欢迎"))
				break;
		}
		br.close();
		s.close();
		System.out.println("当前登录服务关闭");
	}

	@Test
	public void server() throws Exception {
		ServerSocket ss = new ServerSocket(5000);
		while (true) {
			Socket s = ss.accept();
			new Thread(new LoginThread(s)).start();
		}
	}

}

/**
 * 
 * 类名：服务器端验证功能
 * 
 * @author mahao
 * @date 2018年6月9日 Description:
 */
class LoginThread implements Runnable {

	private Socket s;

	public LoginThread(Socket s) {
		this.s = s;
	}

	public void run() {
		String ip = s.getInetAddress().getHostAddress();
		System.out.println(ip+"....connected");
		for(int i=0;i< 3;i++){
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				String name = in.readLine();
				if ("mahao".equals(name)) {
					out.println("欢迎你" + name);
					System.out.println("登录成功");
					break;
				} else {
					out.println("验证失败:" +(2-i)+ " "+ name);
					System.out.println("尝试登录");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
