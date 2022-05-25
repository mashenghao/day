package cn.Ncomposite;

import java.util.List;

/**
 * 文件子类
 * 
 * @author  mahao
 * @date:  2018年10月29日 下午9:06:58
 */
public class File implements IfFile {
	
	private String name;
	
	public File(String name){
		this.name=name;
	}
	
	public boolean add(IfFile f) {
		return false;
	}

	public void show() {
		System.out.println(name);
	}

	public List<IfFile> getChild() {
		return null;
	}

}
