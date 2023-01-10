package eTree.treeSetPack;

/**
 * TreeSet中存储的对象，按照年龄降序排序， 如果年龄相同，判断名字是否相同
 *
 * @author mahao
 * @date: 2019年3月3日 上午11:48:56
 */
public class User implements Comparable<User> {

	private String name;
	private int age;
	private String str;

	@Override
	/**
	 * 实现compareTo方法，使具有排序的能力 1 - 当前对象大 0 - 两对象相等 -1 - 被计较对象大；
	 */
	public int compareTo(User o) {
		if (null == o)
			throw new NullPointerException();
		System.out.println("比较过程--" + this.name + "--compareTo--" + o.getName());
		int num = this.age - o.getAge();
		if (num == 0)// 年龄相同时，比较姓名是否一样，字符串默认实现了Comparable接口
			return this.name.compareTo(o.getName());
		return num;
	}

	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public User(String name, int age, String str) {
		this.name = name;
		this.age = age;
		this.str = str;
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

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
