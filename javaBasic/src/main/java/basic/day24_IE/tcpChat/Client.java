package basic.day24_IE.tcpChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) throws Exception {
		
		Socket s = new Socket("172.21.3.43",8000);
		new Thread(new ReceThread(s)).start();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		while (true) {
			String line = br.readLine();
			out.println(line);
		}
	}
}

