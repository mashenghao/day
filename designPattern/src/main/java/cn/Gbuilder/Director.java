package cn.Gbuilder;
/**
 * 设计者类,用于调用构建器
 * 
 * @author mahao
 *
 */
public class Director {
	
	public static void makeHouse(Builder builder){
		
		builder.make1();
		
		builder.make2();
	}
	
}	
