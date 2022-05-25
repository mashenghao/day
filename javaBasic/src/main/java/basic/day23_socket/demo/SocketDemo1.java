package basic.day23_socket.demo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

/**
 * udp的socket服务建立
 *
 * @author  mahao
 * @date:  2019年3月11日 下午8:39:44
 */
public class SocketDemo1 {
	
	@Test
	public void demo1(){
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(8000);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(!(ss==null)) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//192.168.157.1
	@Test
	public void demo2(){
		Socket s = null;
		try {
			InetAddress add = InetAddress.getLocalHost();
			System.out.println(add);
			s = new Socket(add,8000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(!(s==null)) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
