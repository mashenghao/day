package basic.day19_IO;
import java.io.File;
import java.io.Serializable;

public class User implements Serializable {

	private String name;
	private Integer age;
	private File file;
	
	
	public User(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
}
