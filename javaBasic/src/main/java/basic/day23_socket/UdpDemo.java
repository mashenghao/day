package basic.day23_socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.junit.Test;

/**
 * udp基本操作
 * 
 * @author mahao
 * @date 2018年6月7日 Description:
 */
public class UdpDemo {

	@Test
	public void send() throws Exception {

		// 1.创建UDPsocket连接
		DatagramSocket send = new DatagramSocket();
		// 2.创建数据包
		while (true) {
			BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
			String line = re.readLine();
			byte[] buf = line.getBytes();
			DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 5000);
			// 3.发送信息
			send.send(p);
		}
	}

	@Test
	public void rece() throws Exception {
		// 创建接收指定端口的udpsocket
		DatagramSocket rec = new DatagramSocket(5000);
		// 2.得到连接,将数据存到数据包中
		while (true) {
			byte[] buf = new byte[1024];
			DatagramPacket p = new DatagramPacket(buf, buf.length);
			rec.receive(p);
			// 获取数据
			String ip = p.getAddress().getHostAddress();
			String data = new String(p.getData(), 0, p.getLength());
			int port = p.getPort();
			System.out.println(ip + data + port);
		}

	}
}
