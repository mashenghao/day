package basic.day14_list.setDemo;

public class User {

	private String name;

	public User(String name) {
		this.name = name;
	}

	public User() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	/**
	 * 重写equal方法，让hash值一样是，唯一性判断，按自己<br>
	 * 需求来，如果不更改，则是按照对象地址值进行判断。
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		User u = (User) obj;
		System.out.println(this.name + "-----" + u.getName());
		return (u.getName().equals(name));
	}

	@Override
	/**
	 * 重写hashcode（），让返回值一样，方便进行测试，这样每加入一个元素<br>
	 * hash值是一样的，则会进行equals判断。
	 */
	public int hashCode() {
		return 60;// 实际开发可用 name.hashCode(),减少hash冲突,提高效率
	}

}
