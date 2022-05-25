package cn.Umemento;

/**
 * 用于保存和获得备份对象
 * 
 */
public class Caretaker {
	/** 含有备份对象的引用 */
	private Memento memento;

	public Memento getMemento() {
		return memento;
	}

	public void setMemento(Memento memento) {
		this.memento = memento;
	}

}
