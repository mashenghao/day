package cn.DtestFactory;

/**
 *  两个数相加类
 * 
 */
public class AddOpertion extends Opertion {

	@Override
	public double getResult() {
		return this.getNum1()+this.getNum2();
	}

}
