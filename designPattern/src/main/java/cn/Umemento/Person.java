package cn.Umemento;

/**
 * 需要保存他的状态的对象 ，相当于Originator对象
 */
public class Person {

	private String name;
	private int age;

	/**
	 * 创建备份，用要被备份的类，自己创建备份的 实例，返回一个备份对象Memento
	 */
	public Memento createMemento() {
		return new Memento(name, age);
	}

	/**
	 * 用于恢复备份
	 * @param memento
	 */
	public void setMemento(Memento memento) {
		this.name = memento.getName();
		this.age = memento.getAge();
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
}
