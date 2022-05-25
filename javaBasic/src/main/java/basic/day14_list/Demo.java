package basic.day14_list;

import java.util.Enumeration;
import java.util.Vector;

import org.junit.Test;


public class Demo {
	
	/**
	 * Vector是ArrayList出现之前的集合，
	 * 是线程同步的
	 */
	@Test
	public void demo() {
		Vector v = new Vector<String>();
		Enumeration elements = v.elements();
	}
}
