package cn.KflyWeight;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *	数据库连接池
 */
public class ConnectPool {
	
	private Map<String,Connect> pool;
	
	public ConnectPool(){
		pool = new HashMap<String,Connect>();
	}
	
	public Connect getConnect(String id){
		
		Connect conn = pool.get(id);
		/*
		 *	获取数据库连接时，根据id，从连接池中取。
		 *	若存在连接池，则共用这一个，否则重新
		 *	new出实例，保存到连接池中； 
		 */
		if (conn==null){
			conn = new Connect();
			pool.put(id, conn);
		}
		return conn;
	}
}
