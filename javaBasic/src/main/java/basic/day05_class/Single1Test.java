package basic.day05_class;

import org.junit.Test;

public class Single1Test {
	
	public static void main(String[] args) {
		Single1 s = Single1.getInstance();
		Single1 s2 = Single1.getInstance();
		System.out.println(s);
	}
	
	@Test
	public void Demo1(){
		Single1 s2 = Single1.getInstance();
		System.out.println(s2);
	}

}
