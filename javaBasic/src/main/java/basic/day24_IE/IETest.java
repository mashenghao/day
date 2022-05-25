package basic.day24_IE;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IETest {
	
	public static void main(String[] args) throws Exception {
		Socket s = new Socket("127.0.0.1", 8000);
		
		PrintWriter out = new PrintWriter(s.getOutputStream(),true);
		out.println("GET /myWeb/index.html HTTP/1.1");
		out.println("Accept: */*");
		out.println("Accept-Language: zh-cn");
		out.println("Host: 192.168.1.254:11000");
		out.println("Connection: closed");

		out.println();
		out.println();

		BufferedReader bufr = new BufferedReader(new InputStreamReader(s.getInputStream()));

		String line = null;

		while((line=bufr.readLine())!=null)
		{
			System.out.println(line);
		}

		s.close();

		
	}
}
