package cn.Ncomposite;

import java.util.List;

/**
 *  文件系统目录测试
 * 
 * @author  mahao
 * @date:  2018年10月29日 下午8:21:19
 */
public class MainClass {
	
	public static void main(String[] args) {
		/*
		 * C
		 *   |--a
		 *   	|--d.txt
		 *   	|--e
		 *   		|--f.txt
		 *   		|--g.txt
		 *   |--b
		 *   	|--h
		 *   		|--i.txt
		 *   	|--j
		 */
		Folder C = new Folder("C");
		Folder a = new Folder("a");
		Folder b = new Folder("b");
		Folder e = new Folder("e");
		Folder h = new Folder("h");
		Folder j = new Folder("j");
		File d = new File("d");
		File f = new File("f");
		File g = new File("g");
		File i = new File("i");
		e.add(f);
		e.add(g);
		a.add(d);
		a.add(e);
		C.add(a);
		h.add(i);
		b.add(h);
		b.add(j);
		C.add(b);
		List<IfFile> child = C.getChild();	
		//for (IfFile ifFile : child) {
			printIfFile(C,"|--");
		//}
	}

	private static void printIfFile(IfFile ifFile,String str) {
		str += "--";
		System.out.print(str);ifFile.show();
		if(ifFile instanceof Folder){
			for (IfFile f : ifFile.getChild()) {
				printIfFile(f,str);
			}
		}
	}
	
	
}
