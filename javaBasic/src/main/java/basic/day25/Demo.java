package basic.day25;

public class Demo {

	@MyJunit(param = "test1")
	public void test1(String param) {
		System.out.println("test1-----" + param);
	}

	@MyJunit
	public void test2(String param) {
		System.out.println("test2-----" + param);
	}

	@MyJunit(param = "test3")
	public void test3(String param) {
		System.out.println("test3-----" + param);
	}

	public void test4() {
		System.out.println("test4-----");
	}

	//调用myjunit的触发
	public static void main(String[] args) {

		MainClass.main(new String[] { Demo.class.getName() });
	}
}
