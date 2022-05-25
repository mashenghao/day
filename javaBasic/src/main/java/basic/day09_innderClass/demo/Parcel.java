package basic.day09_innderClass.demo;

public class Parcel {

	public Contents getContents() {
		return new Contents() {

			public int getVal() {
				return (int) (Math.random() * 10);
			}
		}; 
	}
}
