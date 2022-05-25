package basic.day23_socket.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import org.junit.Test;

/**
 * 多线程并发上传文件//
 * TODO:有问题，并发上传的时候出现问题
 *
 * @author  mahao
 * @date:  2019年3月12日 下午12:50:24
 */
public class TcpCopy {
	
	public static void main(String[] args) {
		File[] files = new File[] {
				new File("1.avi"),
				new File("2.avi"),
				new File("3.avi"),
				new File("4.avi"),
				new File("5.avi"),
				new File("6.avi"),
				new File("7.avi"),
				new File("8.avi"),
				new File("9.avi"),
				new File("UQiDong_STA_gw.exe"),
				};
		for(File f : files) { 
			System.out.println(f.getName());
			new Thread(new sendThread(f)).start();
		}
		System.out.println("发送主线主线程---");
	}
	
	@Test
	public void Server() throws InterruptedException {
		try {
			ServerSocket ss = new ServerSocket(8000);
			while(true) {
				Socket socket = ss.accept();
				new Thread(new ReceThread(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

//接收线程
class ReceThread implements Runnable{

	private Socket socket;
	
	public ReceThread(Socket socket){
		this.socket=socket;
	}
	
	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			String fileName = "upload/"+UUID.randomUUID().toString().replaceAll("-", "")+".avi";
			OutputStream fileOut = new FileOutputStream(new File(fileName));
			byte[] buf = new byte[1024*4];
			int len = -1;
			while((len=in.read(buf))!=-1) {
				fileOut.write(buf, 0, len);
				fileOut.flush();
			}
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			out.println(fileName+"--传输完成---服务端发送给客户端的消息");
			System.out.println(Thread.currentThread().getName()+"-----线程服务器接收完毕");
			in.close();
			out.close();
			fileOut.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

//发送线程
class sendThread implements Runnable{

	private File f;
	
	public sendThread(File f){
		this.f = f;
	}
	
	@Override
	public void run() {
		try {
			Socket s = new Socket("192.168.157.1", 8000);
			OutputStream out = s.getOutputStream();
			InputStream fileIn = new FileInputStream(f);
			byte[] buf = new byte[1024*8];
			int len =-1;
			while((len = fileIn.read(buf))!=-1) {
				out.write(buf,0,len);
				out.flush();
			}
			s.shutdownOutput();
			BufferedReader in = new 
					BufferedReader(new InputStreamReader(s.getInputStream()));
			String str = in.readLine();//发送成功回复
			System.out.println(str);
			in.close();
			out.close();
			fileIn.close();
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}