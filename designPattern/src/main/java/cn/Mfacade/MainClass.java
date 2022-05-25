package cn.Mfacade;
/**
 *  外观模式：
 *  
 *  用来提供一个面板，提供调用接口，简化内部操作
 * 
 * @author  mahao
 * @date:  2018年10月29日 下午4:47:12
 */
public class MainClass {
	
	public static void main(String[] args) {
		
		//获取方法调用面板
		Facade facade = new Facade();
		
		//调用方法
		facade.methodA();
		facade.methodB();
		facade.methodC();
	}
}
