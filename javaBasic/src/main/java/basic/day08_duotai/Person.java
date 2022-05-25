package basic.day08_duotai;

/**
 * 父类--person
 *      
 * @author mahao 
 * @date 2019年1月8日 下午4:15:26
 */
public class Person {
	
	public int num=5;
	
	private String name="person";

	public String getName() {
		System.out.println("[name = "+name+"]");
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void doSome() {
		System.out.println("fu---------");
	}
	
	
}
