package cn.Ncomposite;

import java.util.List;

public interface IfFile {
	
	public boolean add(IfFile f);
	
	public void show();
	
	public List<IfFile> getChild();
}
