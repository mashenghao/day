package basic.day02;

/**
 * switch的使用
 * 
 * @author mahao
 * @date 2018年12月16日 下午8:14:26
 */
public class Demo4 {

	/*
	 * default的作用是，当其他没得选择时，是默认的选择，
	 * 当case中一个被选中，数据则会自动向下执行，知道case止步；
	 */
	public static void main(String[] args) {

		int x = 5;
		switch (x) { // switch里面的数据只能是byte , short ,int ,char

		default:
			System.out.println("e");

		case 1:
			System.out.println("a");
			// break;
		case 2:
			System.out.println("b");
			break;
		case 3:
			System.out.println("c");
			break;
		case 4:
			System.out.println("d");
			break;

		}
	}
}
