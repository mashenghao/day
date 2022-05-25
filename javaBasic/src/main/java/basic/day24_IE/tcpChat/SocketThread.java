package basic.day24_IE.tcpChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * 类名：每个用户访问时服务器开的的线程，负责用户消息的转发操作
 * 
 * @author mahao
 * @date 2018年6月10日 Description:
 */
public class SocketThread implements Runnable {

	private Socket s;

	public SocketThread(Socket s) {
		this.s = s;
	}

	public void run() {
		new Thread(new SendThread(s)).start();
		new Thread(new ReceThread(s)).start();
	}

}

/**
 * 类名：发送消息线程
 */
class SendThread implements Runnable {

	private Socket s;

	public SendThread(Socket s) {
		this.s = s;
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			while (true) {
				String line = br.readLine();
				out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

/**
 * 类名：接收消息线程
 */
class ReceThread implements Runnable {

	private Socket s;

	public ReceThread(Socket s) {
		this.s = s;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while (true) {
				String line = in.readLine();
				System.out.println("收----：    " + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}