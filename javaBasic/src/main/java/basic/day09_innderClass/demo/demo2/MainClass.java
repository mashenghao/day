package basic.day09_innderClass.demo.demo2;

/**
 * 匿名内部类使用外部类数据
 *
 * @author mahao
 * @date 2019年4月17日 下午6:32:27
 */
public class MainClass {

	public Base getInnerStr(final int num) {
		return new Base() {
			public String getString() {
				return num + "";
			}
		};
	}

	public static void main(String[] args) {
		int a = 15;

		Base innerStr = new MainClass().getInnerStr(a);
		System.out.println(innerStr.getString());
	}
}
