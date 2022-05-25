package cn.Umemento;

/**
 * 备忘录模式
 * 
 * 行为模式之一，作用是保存对象的 内部状态，在需要时候，可以rollback
 * 
 * @author maho
 *
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 1.创建要被备份的实例
		Person p = new Person("mahao", 25);
		// 2.获得备份的实例
		Memento memento = p.createMemento();
		// 3.更改对象
		p.setAge(22);
		System.out.println("更改后  " + p.toString());
		// 4.恢复备份
		p.setMemento(memento);
		System.out.println("回复备份后  " + p.toString());

		
		// /////////或者
		// 1.创建要被备份的实例
		Person p2 = new Person("lishuo", 18);
		// 2.获得备份的实例,并且保存到备份管理对象中
		Memento memento2 = p.createMemento();
		Caretaker caretaker = new Caretaker();
		caretaker.setMemento(memento2);
		
		// 3.更改对象
		p2.setAge(22);
		System.out.println("更改后  " + p2.toString());
		// 4.恢复备份
		p2.setMemento(caretaker.getMemento());
		System.out.println("回复备份后  " + p2.toString());

	}
}
