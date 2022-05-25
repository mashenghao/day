package basic.day10_exception;

/**
 * 子类重写父类带异常的方法，异常应是父类异常或者子类
 *
 * @author  mahao
 * @date:  2019年2月27日 下午5:14:47
 */
public class ExtendsException {
	
	public static void main(String[] args) {
		Zi zi =new Zi();
		try {
			zi.method();
		} catch (ExceptionA e) {
			e.printStackTrace();
		}				
	}
}



class Fu{
	
	void method() throws ExceptionA{
		System.out.println("父类带异常的方法，抛出ExceptionA异常");
	}
}

class Zi extends Fu{
	@Override
	void method() throws ExceptionB {
		System.out.println("子类重载父类带异常方法，这时候抛出的异常只能是子类");
	}
}

/**异常A*/
class ExceptionA extends Exception{
	
}

/**异常B，继承了异常A*/
class ExceptionB extends ExceptionA{
	
}
