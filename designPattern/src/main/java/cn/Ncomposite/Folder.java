package cn.Ncomposite;

import java.util.ArrayList;
import java.util.List;

/**
 * 目录
 * 
 * @author  mahao
 * @date:  2018年10月29日 下午9:06:18
 */
public class Folder implements IfFile {

	private String name;
	private List<IfFile> child;
	
	public List<IfFile> getChild() {
		return child;
	}
	
	public Folder(String name) {
		this.name = name;
		child = new ArrayList<>();
	}

	public boolean add(IfFile f) {
		return child.add(f);
	}

	public void show() {
		System.out.println(this.name);
	}
}
