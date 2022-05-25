package cn.AsimpleFactory;

/**
 * sql server 数据库的具体实现
 * 
 * @author mahao
 *
 */
public class MMSqlObject implements SQLObject {

	@Override
	public void getConnect() {
		System.out.println("这是sql Server");
	}

}
