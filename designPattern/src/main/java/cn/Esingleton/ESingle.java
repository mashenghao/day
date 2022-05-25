package cn.Esingleton;

/**
 * 饿汉式
 * 
 * @author mahao
 *
 */
public class ESingle {
	
	private static final ESingle single = new ESingle(); 
	
	private ESingle(){
		
	}
	
	public static ESingle getESinge() {
		return single;
	}
}
