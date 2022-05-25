package basic.day09_innderClass.demo;

import org.junit.Test;

/**
 * 匿名内部类的使用
 *
 * @author mahao
 * @date 2019年4月17日 下午6:11:40
 */
public class MainClass {

	// 使用匿名内部类实现
	@Test
	public void demo1() {
		Parcel p = new Parcel();
		Contents contents = p.getContents();
		System.out.println(contents.getVal());
	}

	// 使用匿名内部类实现,王内部匿名内中传递参数
	@Test
	public void demo2() {
		Parcel2 p = new Parcel2();
		Contents2 contents = p.getContents(2);
		System.out.println(contents.getVal());
	}

}
