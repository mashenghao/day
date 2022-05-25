package basic.day23_socket.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.Test;

/**
 * udp聊天
 */
public class UdpDemo {
	
	private int port;
	
	public UdpDemo(int port) {
		this.port = port;
	}

	@Test
	public void clientDemo() {
		DatagramSocket ds =null;
		BufferedReader in =null;
		try {
			ds= new DatagramSocket();
			in =  new BufferedReader(new InputStreamReader(System.in));
			InetAddress add = InetAddress.getByName("192.168.157.255");//广播地址，可以自己聊天
			while(true) {
				String data = in.readLine();
				byte[] buf = data.getBytes();
				DatagramPacket dp = 
						new DatagramPacket(buf, buf.length, add, 8000);
				ds.send(dp);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(ds!=null)
				ds.close();
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
	
	@Test
	public void serverDemo() {
		DatagramSocket ds =null;
		try {
			ds= new DatagramSocket(port);
			byte[] buf = new byte[1024];
			DatagramPacket dp = 
					new DatagramPacket(buf,buf.length);
			while(true) {
				ds.receive(dp);
				InetAddress address = dp.getAddress();
				String ip = address.getHostAddress();
				String data = new String(dp.getData(),0,dp.getLength());
				System.out.println(ip+":"+dp.getPort()+"--收来自--"+data);	
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(ds!=null)
				ds.close();
		}
		
	}
	
	public static void main(String[] args) {
		UdpDemo udpDemo = new UdpDemo(8000);
		new Thread(new Runnable() {
			@Override
			public void run() {
				udpDemo.clientDemo();
			}
		}).start();
		udpDemo.serverDemo();
	}
	
	
}
