package cn.DtestFactory;

/**
 * 1.简单工厂模式，获取子类+ - * / 的实例对象
 * 
 * @author mahao
 *
 */
public abstract class Opertion {
	
	/**
	 * 操作方法
	 * 
	 * @return
	 * @author mahao
	 */
	public abstract double getResult();
	
	private int num1;
	private int num2;
	
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	
}
