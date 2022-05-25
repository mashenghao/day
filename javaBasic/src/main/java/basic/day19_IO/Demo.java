package basic.day19_IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.junit.Test;

public class Demo {

	/**
	 * java 序列化对象：
	 * 
	 * 1.网络传输数据
	 * 
	 * @throws Exception 
	 */
	@Test
	public void demo11() throws  Exception{
		Socket s = new Socket("localhost", 9000);
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		User u = new User("mahao",18);
		u.setFile(new File("E:\\JAVA\\workspaces/javaBasic/upload/01.jpg"));
		out.writeObject(u);
		out.close();
		s.close();
	}
	
	@Test
	public void demo10() throws  Exception{
		ServerSocket ss= new ServerSocket(9000);
		Socket s =ss.accept();
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		User u = (User) in.readObject();
		File f = u.getFile();
		System.out.println(f.getName()+f.getAbsolutePath());
		FileInputStream in1 = new FileInputStream(f);
		FileOutputStream out = new FileOutputStream("E:\\JAVA\\workspaces\\javaBasic\\upload\\00.jpg");
		int len;
		while((len = in1.read())!=-1){
			System.out.println(123);
			out.write(len);
		}
		System.out.println(u);
		in.close();
		s.close();
		ss.close();
	}
	
	/**
	 * java 序列化对象：
	 * 
	 * 1. 持久化保存
	 * 
	 * @throws Exception 
	 */
	@Test
	public void demo9() throws FileNotFoundException, Exception{
		File f = new File("E:\\JAVA\\workspaces/javaBasic/upload/User.txt");
		if(!f.exists())
			f.createNewFile();
		/*
		 * 持久化对象，保存为硬盘上
		 */
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		User u = new User("mahao",18);
		out.writeObject(u);
		out.close();

		/*
		 *重新加载到硬盘上
		 */
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
		User u2 = (User) in.readObject();
		System.out.println(u2);
		in.close();
	}
	
	
	
	/**
	 * Properties对象
	 * @throws IOException
	 */
	@Test
	public void demo8() throws IOException{
		Properties p =new Properties();
		p.load(new FileInputStream("E:\\JAVA\\workspaces/javaBasic/upload/db.properties"));
		System.out.println(p.getProperty("jdbc.url"));
	}
	
	@Test
	public void demo7() throws IOException{
		File f = new File("D:/Tomcat 7.0");
		printFile(f,"|-");
	}
	//  >> 层级递归遍历目录
	public static void printFile(File f ,String s){
		for(File f1 : f.listFiles()){
			if(f1.isFile())
				System.out.println(""+s+f1.getName());
			else{
				System.out.println(s+f1.getName());
				printFile(f1," "+s);
			}
		}
	}

	/**
	 * 文件拷贝
	 * @throws IOException
	 */
	@Test
	public void demo6() throws IOException{
		File f1 = new File("C:\\08.txt");
		File f = new File("C:\\a\\b\\");
		FileInputStream in =new FileInputStream(f1);
		System.out.println(f.exists());
		if(!f.exists()){
			f.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(new File(f,"a.txt"));
		int b =0;
		/*while((b=in.read())!=-1)
		{
			out.write(b);
		}*/
		//System.out.println(f1.renameTo(f));
	}
	
	
	
	@Test
	public void demo5() throws IOException{
		FileInputStream in = new FileInputStream("E:\\JAVA\\workspaces\\javaBasic\\upload\\01.jpg");
		FileOutputStream out = new FileOutputStream("E:\\JAVA\\workspaces\\javaBasic\\upload\\03.jpg");
		int len;
		while((len = in.read())!=-1){
			System.out.println(123);
			out.write(len);
		}
	}
	
	/**
	 * 字符流拷贝文件
	 * @throws IOException 
	 */
	@Test
	public void demo4() throws IOException{
		BufferedInputStream br = new BufferedInputStream(
				new FileInputStream("E:\\JAVA\\workspaces\\javaBasic\\upload\\01.jpg"));
		BufferedOutputStream bo = new BufferedOutputStream(
				new FileOutputStream("E:\\JAVA\\workspaces\\javaBasic\\upload\\02.jpg"));
		
		int len;
		while((len=br.read())!=-1){
			System.out.println(123);
			//System.out.println(len);
			bo.write(len);
		}
		br.close();
		bo.close();
	}
	
	/**
	 * 自定义readLine（）
	 */
	@Test
	public void demo3() throws Exception{
		MyBufferReader mbr = new MyBufferReader
				(new FileReader("E:\\JAVA\\workspaces\\javaBasic\\upload\\MyServer.java"));
		String line=null ;
		while((line=mbr.myReadLine()) != null){
			System.out.println(line);
		}
	}
	
	/**
	 * 带缓冲区的文件拷贝
	 */
	@Test
	public void demo2() throws Exception{
		BufferedReader br = new BufferedReader(
				new FileReader("E:\\JAVA\\workspaces\\javaBasic\\upload\\sql.docx") );
		BufferedWriter bw =new BufferedWriter(new PrintWriter
				(new File("E:\\JAVA\\workspaces\\javaBasic\\upload\\sql_copy.docx")));
		char[] buf = new char[3];
		int len =0;
		while((len=br.read(buf))!=-1){
			System.out.print(new String(buf,0,len));
			bw.write(buf);
		}
		br.close();
		bw.close();
	
	}
	
	/**
	 * 字符流 ： 文件拷贝
	 */
	@Test
	public void demo1() throws Exception{
		FileReader fr = new FileReader("E:\\JAVA\\workspaces\\javaBasic\\upload\\MyServer.java");
		FileWriter fw = new FileWriter("E:\\JAVA\\workspaces\\javaBasic\\upload\\MyServer_copy.java");

		char[] buf = new char[3];
		int len =0;
		while((len=fr.read(buf))!=-1){
			fw.write(buf);
		}
		fw.close();
		fr.close();
	}
}
