package basic.day19_IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

/**
 * 文件的测绘使用
 *
 * @author mahao
 * @date: 2019年3月9日 下午9:21:20
 */
public class FileDemo {

	// 构造器认识
	@Test
	public void demo1() throws IOException {
		// 创建一个文件对象，文件可能不存在
		File f1 = new File("a.txt");
		f1.createNewFile();
		// 指定文件夹下的文件
		File f2 = new File("D:\\Java\\workspaces\\javaBasic\\upload", "b.txt");
		// 父文件下的文件
		File f3 = new File(f1, "c.txt");
		System.out.println(f1);// a.txt
		System.out.println(f2);// D:\Java\workspaces\javaBasic\\upload\b.txt
		System.out.println(f3);// a.txt\c.txt
	}

	// 创建于删除
	@Test
	public void demo2() throws IOException {
		// 创建一个文件，必须存在文件目录,存在目录，返回假
		File f1 = new File("a.txt");
		boolean b1 = f1.createNewFile();
		System.out.println(b1);
		// 如果创建不存在的文件夹下的文件，则会抛出异常
		File f2 = new File("D:\\Java2\\workspaces\\javaBasic", "b.txt");
		// boolean b2 = f2.createNewFile();
		// System.out.println(b2);
		// 删除文件
		boolean b3 = f1.delete();
		System.out.println("file is deleted success ? " + b3);

		// 创建目录
		File f4 = new File("c:/upload/newDir");
		File f5 = new File("c:/upload2/newDir");
		boolean b4 = f4.mkdir();// 只能创建一级目录
		boolean b5 = f5.mkdirs();// 可以创建多级目录
		System.out.println("创建一级目录成功 " + b4);
		System.out.println("创建多级目录成功 " + b5);
	}

	// file function -判断
	@Test
	public void demo3() throws IOException {
		File f1 = new File("a.txt");
		File f2 = new File("D:\\Java\\workspaces\\javaBasic");
		System.out.println("文件是否存在" + f1.exists());// **
		System.out.println("目录是否存在" + f2.exists());
		System.out.println("文件可读 -可写" + f1.canRead() + f1.canWrite());
		// 磁盘绝对路径
		System.out.println("文件绝对地址" + f1.getAbsolutePath());// **
		System.out.println("目录绝对地址" + f2.getAbsolutePath());
		System.out.println("文件名" + f1.getName());// a.txt //**
		System.out.println("目录名" + f2.getName());// javaBasic
		// 将抽象路径名，转变成路径，在创建对象是传入的字符串
		System.out.println(f1.getPath());
		System.out.println(f2.getPath());
		// 文件还是目录
		System.out.println("是文件吗 " + f1.isFile());
		System.out.println("是否是目录" + f2.isDirectory());

		// 练习： 将文件放在C:/upload文件夹下，存入文件按天数存放
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String path = sdf.format(new Date());
		// 父目录路径+子目录路径
		File parentFile = new File("c:/upload", path);
		System.out.println(parentFile);
		while (!parentFile.exists())// 文件目录不存在，一直创建文件夹
			parentFile.mkdirs();
		for (int i = 0; i < 10; i++) {
			// 父目录文件，文件名
			File f = new File(parentFile, i + ".txt");
			System.out.println("文件的绝对路径" + f.getAbsolutePath());
			System.out.println("文件名是 ；" + f.getName());
			f.createNewFile();
		}
	}

	// 文件 --获取
	@Test
	public void demo4() throws IOException {
		File f1 = new File("a.txt");
		File f2 = new File("D:\\Java\\workspaces\\javaBasic\\.txt");
		// 获得绝对路径
		String p1 = f1.getAbsolutePath();// 获取绝对路径
		String p2 = f1.getPath();// 获取封装的路径
		//
		System.out.println(f2.getParent());// 封装的路径的上一级目录
		System.out.println(f1.length());

		// 文件剪切
		File f3 = new File("a.txt");
		File f4 = new File("upload/aa-cut.txt");
		System.out.println(f3.renameTo(f4));
	}

	// 文件 --递归遍历文件夹
	@Test
	public void demo5() throws IOException {
		File dir = new File("D:\\C语言");
		// showDir(dir, "--");

		MyFileDir myFileDir = workFile(dir);
		System.out.println(myFileDir);
	}

	private MyFileDir workFile(File dir) {
		MyFileDir myFileDir = new MyFileDir();
		List<MyFileDir> child = null;
		myFileDir.setFile(dir);
		if (dir.isFile()) {
			myFileDir.setChild(child);
		} else {
			child = new ArrayList<MyFileDir>();
			for (File f : dir.listFiles()) {
				MyFileDir workFile = workFile(f);
				child.add(workFile);
			}
			myFileDir.setChild(child);
		}
		return myFileDir;
	}

	private void showDir(File d, String prex) {
		if (d.isDirectory()) {
			for (File f : d.listFiles()) {
				showDir(f, prex + "--");
			}
		} else {
			System.out.println(prex + d.getName());
		}
	}

	// 删除带内容的文件目录--从里面往外删除
	@Test
	public void demo6() {
		File f = new File("c:/upload");
		System.out.println(deleteDir(f));
	}

	private boolean deleteDir(File dir) {
		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else {
				deleteDir(f);
			}
		}
		return dir.delete();// 删除文件后，删除目录
	}

	@Test
	public void demo7() throws IOException {
		Properties p = new Properties();
		// 存 取
		p.setProperty("key1", "v1");
		p.setProperty("key2", "v2");
		String v1 = p.getProperty("key1");
		System.out.println(v1);
		// 存取文件
		p.load(new FileInputStream("upload/demo.properties"));
		String name = p.getProperty("name", "默认值");
		System.out.println(name);
		p.setProperty("name", "new Name");
		p.store(new FileOutputStream("upload/demo.properties"), "");
	}

	// 打印流
	@Test
	public void demo8() throws IOException {
		PrintStream out1 = new PrintStream(new File("upload/demo1.txt"));
		out1.println("嘚瑟安保费；吧");
		// 字节输出流
		PrintWriter out2 = new PrintWriter("");
	}

	// 序列化
	@Test
	public void demo9() throws IOException, ClassNotFoundException {
		//写入对象
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("upload/fileDir.obj"));
		out.writeObject(new User("mahao",18));
		//获取对象
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("upload/fileDir.obj"));
		User user=  (User) in.readObject();
		System.out.println(user);
	}
}

class MyFileDir {
	private File file;
	private List<MyFileDir> child;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<MyFileDir> getChild() {
		return child;
	}

	public void setChild(List<MyFileDir> child) {
		this.child = child;
	}
}
