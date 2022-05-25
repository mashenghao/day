package basic.day23_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * UDP实现仿qq双向聊天的功能
 * 
 * @author mahao
 * @date 2018年6月7日 Description:
 */

/*
 * 思路：
 * 两个线程，一个发送信息，另一个接收消息
 * 
 */
public class UdpDemo2 {
	
	public static void main(String[] args) throws Exception {
		DatagramSocket se = new DatagramSocket();
		DatagramSocket re = new DatagramSocket(8000);
		
		new Thread(new Send(se)).start();
		new Thread(new Rece(re)).start();
	}
}

/**
 * 
 * 类名：发送消息使用
 * 
 * @author mahao
 * @date 2018年6月7日 Description:
 */
class Send implements Runnable {

	private DatagramSocket sendSocket;

	public Send(DatagramSocket d) {
		this.sendSocket = d;
	}

	public void run() {
		// 2.数据包
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String data = null;
		try {
			while ((data = br.readLine()) != null) {// 给172.21.3.28 主机发送消息
				byte[] buf = data.getBytes();
				DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getByName("172.21.3.43"), 7000);
				sendSocket.send(p);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class Rece implements Runnable {

	private DatagramSocket receSocket;

	public Rece(DatagramSocket d) {
		this.receSocket = d;
	}

	public void run() {

		while (true) {// 接收来这个地方传的消息
			byte[] buf = new byte[1024];
			DatagramPacket p = new DatagramPacket(buf, buf.length);
			try {
				receSocket.receive(p);
				System.out.println(p.getPort()+" 收到消息  " + p.getAddress().getHostAddress() + "  "
						+ new String(p.getData(), 0, p.getData().length));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
