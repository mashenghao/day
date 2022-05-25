package cn.AsimpleFactory;

/**
 * mysql的实现
 * 
 * @author mahao
 *
 */
public class MysqlObject implements SQLObject {

	@Override
	public void getConnect() {
		
		System.out.println("这是mysql");
	}


}
