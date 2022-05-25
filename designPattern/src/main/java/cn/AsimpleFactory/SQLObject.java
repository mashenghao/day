package cn.AsimpleFactory;
/**
 * 数据库对象的父类接口
 * 
 * @author mahao
 *
 */
public interface SQLObject {
	
	/** 获得本对象的连接*/
	void getConnect();
	
}
