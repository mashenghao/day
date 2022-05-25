package basic.day10_exception;

/**
 * RuntimeException异常特殊性
 *
 * @author mahao
 * @date: 2019年2月26日 下午1:35:09
 */
public class Demo1 {

	public static void main(String[] args) {
		div2(0);  //不需要声明和处理
	}

	/*
	 * 函数内抛出异常，则需要在函数上声明，并且处理
	 */
	public static void div(int n) throws MyException {

		if (n == 0)
			throw new MyException("除数不能为0----");

		System.out.println(123);
	}

	/*
	 * 这种则不要声明
	 */
	public static void div2(int n){

		if (n == 0)
			throw new RuntimeException();

		System.out.println(123);
	}
}
