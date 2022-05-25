package basic.day24_IE.tcpChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类名：tcp方式聊天，服务端
 * 
 * @author mahao
 * @date 2018年6月10日 Description:
 */

public class Server {

	public static void main(String[] args) throws Exception {

		ServerSocket ss = new ServerSocket(8000);
		List<Socket> list = new ArrayList<Socket>();
		while (true) {
			Socket s = ss.accept();
			list.add(s);
			System.out.println("当前上线人数为：---------------    "+list.size());
			new Thread(new ServerT(s, list)).start();
		}
	}
}

class ServerT implements Runnable {
	private Socket s;
	private List<Socket> list;

	public ServerT(Socket s, List<Socket> list) {
		this.list = list;
		this.s = s;
	}

	public void run() {
		while (true) {
			BufferedReader in;
			try {
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					for (Socket ss : list) {
						System.out.println(line+"  "+ss.getInetAddress().getHostAddress()+" "+ss.getPort());
						PrintWriter out = new PrintWriter(ss.getOutputStream(), true);
						out.println(line);
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}