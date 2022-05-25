package basic.day14_map;

public class Parent {
	
	private String name;

	
	public Parent() {
		super();
	}

	public Parent(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Parent [name=" + name + "]";
	}
	
	
}
