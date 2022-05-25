package basic.day23_socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class TcpDemo {

	@Test
	public void client() throws Exception {
		Socket s = new Socket("172.21.3.43", 8000);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
		BufferedReader brs = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String line;
		while (true) {
			while ((line = br.readLine()) != null) {
				pw.println(line);
				System.out.println("客户机收到： " + brs.readLine());
			}
		}
	}

	@Test
	public void server() throws Exception {
		ServerSocket ss = new ServerSocket(8000);
		Socket s = ss.accept();
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
		BufferedReader brs = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = br.readLine();
			System.out.println("服务机收到：：" + line);
			pw.println(br.readLine());
		}
	}

}
