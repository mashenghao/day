package basic.day05_class;

import org.junit.Test;

public class Single2Test {
	
	public static void main(String[] args) {
		Single2 s = Single2.getInstance();
		Single2 s2 = Single2.getInstance();
		System.out.println(s==s2);
	}
	
	@Test
	public void Demo1(){
		Single1 s2 = Single1.getInstance();
		System.out.println(s2);
	}

}
