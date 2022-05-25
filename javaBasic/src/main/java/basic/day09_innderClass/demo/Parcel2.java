package basic.day09_innderClass.demo;

public class Parcel2 {

	public Contents2 getContents(int num) {
		System.out.println(num);
		return new Contents2() {
			public int getVal() {
				System.out.println(num);
				return (int) (Math.random() * 100);
			}
		};
	}
}
