package cn.AsimpleFactory;

/**
 * 01 简单工厂模式
 * 
 * 案列：不同数据库连接对象<br>
 * 
 * 不同用户获取数据不同的数据库操作对象，通过工厂， 返回用户所需要的对象
 * 
 * @author mahao
 *
 */
public class MainClass {

	/**
	 * 
	 * 用户通过工厂，获取到了不同的数据库实例对象，用户不必在意如何创建成功
	 * 的这个对象，用户只需要使用这个创建好的对象那个，使用里面相同的方法
	 * 
	 */
	public static void main(String[] args) throws Exception {
		SQLObject mysqlObj = SQLFactory.getSqlObject("mysql");
		SQLObject sqlObj = SQLFactory.getSqlObject("sql server");
		mysqlObj.getConnect();
		sqlObj.getConnect();
	}
}
