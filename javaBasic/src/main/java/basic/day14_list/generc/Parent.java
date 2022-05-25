package basic.day14_list.generc;

public class Parent implements Comparable<Parent>{
	
	/*
	 * ? super E,泛型限定，这样就是使子类几倍了父类的能力
	 */
	public int compareTo(Parent o) {
		return 1;
	}
	
}
