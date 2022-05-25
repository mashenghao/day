package basic.day02;

import org.junit.Test;

/**
 * 逻辑运算符
 * 
 * & | ! ^ && ||
 *
 * @author  mahao
 * @date:  2018年12月15日 下午6:33:16
 */
public class Demo2 {
	
	@Test
	public void test1() {
		int x =3;
		boolean flag = x<5&x>1;
		System.out.println("与运算符结果为"+flag);
		System.out.println("或运算结果为"+(true|false));
		System.out.println("^异或运算结果为"+(true^false)+"---"+(true^true));
	}
}
