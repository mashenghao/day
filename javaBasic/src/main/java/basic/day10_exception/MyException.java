package basic.day10_exception;

/**
 * 自定义异常
 *
 * @author  mahao
 * @date:  2019年2月26日 下午1:38:59
 */
public class MyException extends Exception{
	
	public MyException() {
		super();
	}
	
	public MyException(String str){
		super(str);
	}
	

}
