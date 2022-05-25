package basic.day23_socket;

import org.junit.Test;

abstract class Shape {
	public double l = 1.2;

	abstract double area();

	public Shape(double l) {
		this.l = l;
	}
}

class Circle extends Shape {
	public double l;

	public Circle(double l) {
		super(l);
		this.l = l;
	}

	double area() {
		return 3.14 * l * l;
	}
}

public class testB {
	public static void main(String[] args) {
		Circle c = new Circle(1.5);
		System.out.print(c.area());
	}

	@Test
	public void ADemo() {
		int[] a = new int[3];
		int[] b = a;
		a[1] = 3;
		// b[1]=5;
		System.out.println(b[1]);
	}
}
