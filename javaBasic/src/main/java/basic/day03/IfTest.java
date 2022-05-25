package basic.day03;

/**
 * 练习if-switch
 *
 * @author  mahao
 * @date:  2019年2月26日 上午12:16:06
 */
public class IfTest {
	
	public static void main(String[] args) {
		boolean b = true;
		if(b=false) //可以在括号里面进行逻辑运算
			System.out.println(0);
		else if(!b)
			System.out.println(1);
		else 
			System.out.println(2);
	}
}
