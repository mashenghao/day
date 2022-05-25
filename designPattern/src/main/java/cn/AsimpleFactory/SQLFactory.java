package cn.AsimpleFactory;

public class SQLFactory {

	/**
	 * 得到数据库实体对象
	 * 
	 * @return
	 * @author mahao
	 * 
	 */
	public static SQLObject getSqlObject(String type) throws InstantiationException, IllegalAccessException {
		
		if ("mysql".equals(type)) {
			return MysqlObject.class.newInstance();
		} else if ("sql server".equals(type)) {
			return MMSqlObject.class.newInstance();
		} else {
			System.out.println("找不到实例对象---");
			return null;
		}
	}
	
}
