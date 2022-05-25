package basic.day23_socket.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

/**
 * tcp通话服务
 *
 * @author  mahao
 * @date:  2019年3月11日 下午10:00:06
 */
public class TcpDemo {
	
	@Test
	public void client() {
		Socket s =null;
		BufferedReader in= null;
		PrintStream out = null;
		try {
			s= new Socket("192.168.157.1",8000);//访问连接主机和端口号
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintStream(s.getOutputStream());//带自动刷新
			String line=null;
			while((line=in.readLine())!=null) {
				out.println(line);
				if(line.equals("over")) {
					out.close();
					return;
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(in!=null)
				try {in.close();} catch (IOException e) {}
			if(out!=null)
				try {out.close();} catch (Exception e) {}
			if(s!=null)
				try { s.close();} catch (IOException e) {}
			
		}
	}
	
	@Test
	public void Server() {
		ServerSocket ss=null;
		try {
			 ss= new ServerSocket(8000);
			Socket socket = ss.accept();
			BufferedReader in =new BufferedReader( new InputStreamReader(socket.getInputStream()));
			while(true) {
				String line = in.readLine();
				System.out.println(line);
				if("over".equals(line)) {
					socket.close();
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
