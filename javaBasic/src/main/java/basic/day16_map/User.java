package basic.day16_map;

public class User {
	
	private String name;
	private String pass;
	
	/**
	 * 重写hashcode（）和equals方法，自定义比较
	 */
	
	// >> 改写hashcode，只有具备相同的hash值对象，才能比较
	public int hashCode() {
		return 15;
	}
	
	/**
	 * 比较过程，新添加数据，为当前对象，他从集合中获取数据，
	 * 对每一个与自己进行比较，obj则是与自己比较的数据 
	 */
	// >> 根据用户名和pass比较
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		User u = (User)obj;
		System.out.println(u.getName()+"+++++++++++");
		return u.getName().equals(name)&&u.getPass().equals(pass);
	}
	
	public User(String name) {
		this.name= name;
	}
	
	public User(String name, String pass) {
		this.name= name;
		this.pass=pass;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

}
