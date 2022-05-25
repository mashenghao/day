package cn.Mfacade;

/**
 * 方法调用面板
 * 
 * 方法涉及三个系统，如果调用的话，这需要三个系统 调用，因此，这里提供方法，内部包装了这个方法的 调用。
 * 
 * @author mahao
 * @date: 2018年10月29日 下午4:48:47
 */
public class Facade {
	
	private SystemA systemA;
	private SystemB systemB;
	private SystemC systemC;
	
	public Facade(){
		systemA = new SystemA();
		systemB = new SystemB();
		systemC = new SystemC();
	}
	
	/** 调用系统A，和系统B*/
	public void methodA() {
		systemA.getA();
		systemB.getB();
	}
	
	/**调用系统A，和系统B,系统C*/
	public void methodB() {
		systemA.getA();
		systemB.getB();
		systemC.getC();
	}
	
	/** 调用C */
	public void methodC() {
		systemC.getC();
	}
}
