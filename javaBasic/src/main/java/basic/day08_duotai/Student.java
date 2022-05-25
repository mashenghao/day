package basic.day08_duotai;

/**
 * 子类--学生
 *      
 * @author mahao 
 * @date 2019年1月8日 下午4:15:15
 */
public class Student extends Person{
	
	public int num=8;
	
	private String no = "2018001";

	public String getNo() {
		System.out.println("[no = "+no+"]");
		return no;
	}
	
	public String getName() {
		System.out.println("[name = student ]");
		return "student";
	}
	
	public void setNo(String no) {
		this.no = no;
	}

	public static void doSome() {
		System.out.println("zi----------");
	}
	
	 
}
